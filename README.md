# curl-er
POST /send

		{
			"from" : timestamp in epoch milli  
			"to"   : timestamp in epoch milli
		}

POST /spam-to-app

		{
			 "period" : 1,
			  "path" : "migrate-reg-addresses",
			  "message" : "
				       {
					'sourceSystem': ppot,
					'sourceSubSystem': 'citizenship',
					'targetSystem': 'cit'
				       }
                                      "
}

POST /find-file

		{
			"file" : "filename .csv or .txt"
		}

POST /find-file-not-csv

		{
			"file" : "file with various symbols but with proper data values (string, string, string, dd/MM/yyyy or dd-MM-yyyy) "
		}


POST /find-json

		[
  			{			
				"fname" : "first name 1",
				"lname" : "last name 1",
				"mname" : "middle name 1",
				"date" : "date in dd/MM/yyyy format 1"
  			},
  				...
  			{
				"fname" : "first name N",
				"lname" : "last name N",
				"mname" : "middle name N",
				"date" : "date in dd/MM/yyyy format N"
  			}
		]
