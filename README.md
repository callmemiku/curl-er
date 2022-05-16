# curl-er
POST /send

		{
			"from" : timestamp in epoch milli  
			"to"   : timestamp in epoch milli
		}

POST /find-files

		{
			"file" : "filename .csv or .txt"
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
