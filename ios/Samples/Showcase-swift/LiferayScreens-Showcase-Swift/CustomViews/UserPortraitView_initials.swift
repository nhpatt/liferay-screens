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
import UIKit
import LiferayScreens

class UserPortraitView_initials: UserPortraitView_default {

	@IBOutlet weak var initalsLabel: UILabel!

	override func loadPlaceholder(for user: User) {
		portraitImage?.image = nil

		let nameInitial = String(user.firstName.characters.first!).uppercased()
		let surnameInitial = String(user.lastName.characters.first!).uppercased()

		initalsLabel.text = "\(nameInitial)\(surnameInitial)"
		initalsLabel.isHidden = false
	}
}
