package com.pavelsklenar.rest.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavelsklenar.rest.pojo.RequestBody;
import com.pavelsklenar.rest.pojo.ResponseBody;
import com.pavelsklenar.rest.pojo.ResponseImage;
import com.pavelsklenar.rest.pojo.ResponseImageBody;
import com.pavelsklenar.rest.pojo.ResponseImageInfo;
import com.pavelsklenar.rest.pojo.ResponseProviderInfo;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

	@PostMapping()
    public ResponseBody getAttachmentResponse(RequestBody request) {
		ResponseBody response = new ResponseBody();
		response.getImageRs = new ResponseImage();
		response.getImageRs.providerInfo=new ArrayList<>();
		ResponseProviderInfo providerInfo = new ResponseProviderInfo();
		providerInfo.code="1";
		providerInfo.detail="SUCCESS";
		response.getImageRs.providerInfo.add(providerInfo);
		response.getImageRs.responseBody=new ResponseImageBody();
		response.getImageRs.responseBody.imageResponse=new ResponseImageInfo();
		response.getImageRs.responseBody.imageResponse.contentType="pdf";
		response.getImageRs.responseBody.imageResponse.imageContent="JVBERi0xLjQKJeLjz9MKMyAwIG9iago8PC9JbnRlbnQvUGVyY2VwdHVhbC9EZWNvZGVQY\r\n" + 
				"XJtczw8L0NvbG9ycyAzL1ByZWRpY3RvciAxNS9CaXRzUGVyQ29tcG9uZW50IDgvQ29sdW1ucyAxNzQ+Pi9UeXBlL\r\n" + 
				"1hPYmplY3QvQ29sb3JTcGFjZVsvQ2FsUkdCPDwvTWF0cml4WzAuNDEyMzkgMC4yMTI2NCAwLjAxOTMzIDAuMz\r\n" + 
				"U3NTggMC43MTUxNyAwLjExOTE5IDAuMTgwNDUgMC4wNzIxOCAwLjk1MDRdL0dhbW1hWzIuMiAyLjIgMi4yXS\r\n" + 
				"9XaGl0ZVBvaW50WzAuOTUwNDMgMSAxLjA5XT4+XS9TdWJ0eXBlL0ltYWdlL0Jpd\r\n" + 
				"cmFpbGVyCjw8L1Jvb3QgNjQgMCBSL0lEIFs8MzFlNzhkMWZmYTQ2ZTNhM2MzYjA0MDcyZDRjMjExYmI+PDMxZTc\r\n" + 
				"4ZDFmZmE0NmUzYTNjM2IwNDA3MmQ0YzIxMWJiPl0vSW5mbyA2NSAwIFIvU2l6ZSA2Nj4+CiVpVGV4dC01LjUu\r\n" + 
				"OApzdGFydHhyZWYKMTQxMjg2CiUlRU9GCg==";
		return response;
    }
}
