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

package com.liferay.analytics.android.util

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author Igor Matos
 */
internal class HttpClient {
	companion object {

		@Throws(IOException::class)
		fun post(url: String, json: String, timeout: Long = 30): String {

			val body = RequestBody.create(MEDIA_TYPE, json)

			val request = Request.Builder()
				.url(url)
				.post(body)
				.build()

			val client = OkHttpClient.Builder()
				.readTimeout(timeout, TimeUnit.SECONDS)
				.writeTimeout(timeout, TimeUnit.SECONDS)
				.connectTimeout(timeout, TimeUnit.SECONDS)
				.trust(CERTIFICATE_DST_CA_X3)
				.build()

			val response = client.newCall(request).execute()

			response.body()?.let {
				return it.string()
			}

			return ""
		}

		private const val CERTIFICATE_DST_CA_X3 = "certificates/DST Root CA X3"
		private val MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8")
	}
}