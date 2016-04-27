var appRouter = function(app) {

	var mysql      = require('mysql');
	var connection = mysql.createConnection({
	  host     : 'db.ist.utl.pt',
	  user     : 'ist173932',
	  password : 'jkiz2179',
	  database : 'ist173932'
	});
	connection.connect();

	/////////////////////////////////////
	app.get("/", function(req, res) {
    		res.send("Hello World\n");
	});

	app.get("/user", function(req, res) {
			if(!req.query.username) {
        	return res.send({"status": "error", "message": "missing username"});
    	}
			else {
				connection.query('SELECT password FROM user WHERE username=\''+req.query.username+'\';', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}
				res.status(202).send(rows);
				console.log(rows);
			});
		}
	});

		app.post("/account", function(req, res) {
	    if(!req.body.username || !req.body.password) {
	        return res.send({"status": "error", "message": "missing a parameter"});
	    } else {
				connection.query('INSERT INTO user (username, password) VALUES(\''+req.body.username+'\' ,\''+req.body.password+'\');', function(err, rows, fields) {
					if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
										}
					res.status(203).send("Wrong with de database");
				}
				res.status(200).send("OK");
				console.log(rows);
			});
		}
	});

	app.delete("/userdelete", function(req, res) {
		if(!req.query.username) {
				return res.send({"status": "error", "message": "missing a parameter"});
		} else {
			connection.query('DELETE FROM user WHERE username=\''+req.query.username+'\';', function(err, rows, fields) {
				if (err){
				if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
					node.exit();
									}
				res.status(203).send("Wrong with de database");
			}
			res.status(200).send("OK");
			console.log(rows);
		});
	}
});

}

module.exports = appRouter;
