/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/

import Foundation
import UIKit

@objc open class ScreensCordovaWebView: NSObject, ScreensWebView {

	open var view: UIView {
		return cordovaVC.view
	}

	var scriptsToInject = [InjectableScript]()

	lazy var cordovaVC: ScreensCordovaViewController = ScreensCordovaViewController(
		jsCallHandler: self.jsCallHandler, onPageLoadFinished: {[weak self] error in self?.onPageLoad(error: error)})

	let jsCallHandler: (String, String) -> Void
	let onPageLoadFinished: (Error?) -> Void
	let jsErrorHandler: (String) -> (Any?, Error?) -> Void

	public required init(jsCallHandler: @escaping (String, String) -> Void,
		jsErrorHandler: @escaping (String) -> (Any?, Error?) -> Void, onPageLoadFinished: @escaping (Error?) -> Void) {

		self.jsCallHandler = jsCallHandler
		self.jsErrorHandler = jsErrorHandler
		self.onPageLoadFinished = onPageLoadFinished

		super.init()

		let plugin = loadCordovaPlugin()
		self.scriptsToInject.append(plugin)
	}
	
	open func add(injectableScript: InjectableScript) {
		scriptsToInject.append(injectableScript)
	}

	open func inject(injectableScript: InjectableScript) {
		cordovaVC.inject(script: injectableScript, completionHandler: jsErrorHandler(injectableScript.name))
	}

	open func load(request: URLRequest) {
		cordovaVC.load(request: request)
	}

	open func load(htmlString: String) {
		cordovaVC.load(htmlString: htmlString)
	}

	open func onPageLoad(error: Error?) {
		if error != nil {
			onPageLoadFinished(error)
			return
		}

		self.scriptsToInject.forEach { cordovaVC.inject(script: $0, completionHandler: jsErrorHandler($0.name)) }
		self.onPageLoadFinished(nil)
	}

	open func handleJsCalls(uri: String) -> Bool {
		if uri.hasPrefix("screens-") {
			let parts = uri.components(separatedBy: "://")
			let key = parts[0].components(separatedBy: "-")[1]
			let message = parts[1]

			jsCallHandler(key, message)

			return true
		}

		return false
	}

	open func loadCordovaPlugin() -> InjectableScript {
		guard Bundle.main.path(forResource: "www/cordova", ofType: "js") != nil
		else {
			fatalError("If you enable cordova you have to add the cordova scripts to the project")
		}

		var jsPaths = [String]()

		jsPaths.append("www/cordova.js")

		let directoryEnumerator = FileManager.default.enumerator(atPath: Bundle.main.path(forResource: "www/plugins", ofType: nil)!)

		while let path = directoryEnumerator?.nextObject() as? String {
			if path.hasSuffix(".js") {
				jsPaths.append("www/plugins/\(path)")
			}
		}

		jsPaths.append("www/cordova_plugins.js")

		let fullScript = jsPaths
			.map { Bundle.main.path(forResource: $0, ofType: nil) }
			.flatMap { $0 }
			.map { try! String(contentsOfFile: $0) }
			.joined(separator: "\n")

		return JsScript(name: "Cordova.js", js: fullScript)
	}
}
