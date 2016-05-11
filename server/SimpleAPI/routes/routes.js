var appRouter = function(app) {

	var mysql      = require('mysql');
	var connection = mysql.createConnection({
	  host     : 'db.ist.utl.pt',
	  user     : 'ist173932',
	  password : 'jkiz2179',
	  database : 'ist173932'
	});

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
				}else {
					res.status(202).send(rows);
					console.log(rows);
				}

			});
		}
	});

	app.get("/availableStations", function(req, res) {

				connection.query('SELECT localizacao FROM bicicleta WHERE status = false GROUP BY localizacao', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(202).send(rows);
					console.log(rows);
				}

			});
		});



	app.get("/pontos", function(req, res) {
			if(!req.query.username) {
        	return res.send({"status": "error", "message": "missing username"});
    	}
			else {
				connection.query('SELECT pontos FROM user WHERE username=\''+req.query.username+'\';', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(202).send(rows);
					console.log(rows);
				}

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
					res.status(203).send("Wrong with the database");
				}else {
					res.status(200).send("OK");
					console.log(rows);
				}

			});
		}
	});

	app.post("/userdelete", function(req, res) {
		if(!req.body.username) {
				return res.send({"status": "error", "message": "missing a parameter"});
		} else {
			connection.query('DELETE FROM user WHERE username=\''+req.body.username+'\';', function(err, rows, fields) {
				if (err){
				if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
					node.exit();
									}
				res.status(203).send("Wrong with the database");
			}
			res.status(200).send("OK");
			console.log(rows);
		});
	}
});

app.post("/updatepass", function(req, res){
	if(!req.body.username || !req.body.novapassword) {
			return res.send({"status": "error", "message": "missing a parameter"});
	}else {
		connection.query('UPDATE user SET password=\''+req.body.novapassword+'\' WHERE username=\''+req.body.username+'\';', function(err, rows, fields) {
							if (err){
								if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
									node.exit();
								}
								res.status(203).send("Wrong query");
							}else {
								res.status(200).send('OK');
								console.log(rows);
							}
					});
			}
		});



		app.post("/setpoints", function(req, res){
			if(!req.body.username || !req.body.points) {
					return res.send({"status": "error", "message": "missing a parameter"});
			}else {
				connection.query('UPDATE user SET pontos=\''+req.body.points+'\' WHERE username=\''+req.body.username+'\';', function(err, rows, fields) {
									if (err){
										if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
											node.exit();
										}
										res.status(203).send("Wrong query");
									}else {
										res.status(200).send('OK');
										console.log(rows);
									}
							});
					}
				});

		app.get("/bike", function(req, res) {
			if(!req.query.stationid) {
        	return res.send({"status": "error", "message": "missing stationid"});
    	}
			else {
				connection.query('SELECT bikeid FROM bicicleta WHERE bicicleta.localizacao =\''+req.query.stationid+'\'AND status = false;', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(202).send(rows);
					console.log(rows);
				}

			});
		}
	});

		app.get("/myfriends", function(req, res) {
			if(!req.query.username) {
        	return res.send({"status": "error", "message": "missing username"});
    	}
			else {
				connection.query('SELECT username2 FROM amigos WHERE username1=\''+req.query.username+'\'OR (select username1 from amigos where username2=\''+req.query.username+'\');', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(200).send(rows);
					console.log(rows);
				}

			});
		}
	});

		app.post("/addfriend", function(req, res) {
			if(!req.body.username1 || !req.body.username2 ) {
        	return res.send({"status": "error", "message": "missing username"});
    	}
			else {
				connection.query('INSERT INTO amigos (username1, username2) VALUES (\''+req.body.username1+'\' ,\''+req.body.username2+'\');', function(err, rows, fields) {
				//connection.query('INSERT INTO user (username, password) VALUES(\''+req.body.username+'\' ,\''+req.body.password+'\');', function(err, rows, fields) {

				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(200).send("OK");
					console.log(rows);
				}

			});
		}
	});

		app.get("/getUsers", function(req, res) {
				connection.query('SELECT username FROM user;', function(err, rows, fields) {
				  if (err){
					if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
						node.exit();
                    }
					res.status(203).send("Wrong query");
				}else {
					res.status(200).send(rows);
					console.log(rows);
				}
			});
	});

	app.post("/pickUp", function(req, res){
			if(!req.body.bikeid) {
					return res.send({"status": "error", "message": "missing bikeid"});
			}else {
				connection.query('UPDATE bicicleta SET status = 1 where bikeid = \''+req.body.bikeid+'\';', function(err, rows, fields) {
									if (err){
										if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
											node.exit();
										}
										res.status(203).send("Wrong query");
									}else {
										res.status(200).send('OK');
										console.log(rows);
									}
				});
			}
	});

	app.post("/leaveBike", function(req, res){
			if(!req.body.bikeid) {
					return res.send({"status": "error", "message": "missing bikeid"});
			}else {
				connection.query('UPDATE bicicleta SET status = 0 where bikeid = \''+req.body.bikeid+'\';', function(err, rows, fields) {
									if (err){
										if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
											node.exit();
										}
										res.status(203).send("Wrong query");
									}else {
										res.status(200).send('OK');
										console.log(rows);
									}
				});
			}
	});

	app.post("/newroute", function(req, res){
			if(!req.body.bikeid || !req.body.username) {
					return res.send({"status": "error", "message": "missing bikeid"});
			}else {
				connection.query('INSERT INTO rotas (username, bikeid) VALUES(\''+req.body.username+'\' ,\''+req.body.bikeid+'\');', function(err, rows, fields) {
									if (err){
										if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
											node.exit();
										}
										res.status(203).send("Wrong query");
									}else {
										res.status(200).send('rows.insertId');
										console.log(rows);
									}
				});
			}
	});

	app.post("/adicionarcoordenada", function(req, res){
			if(!req.body.longitude || !req.body.latitude || !req.body.routeid) {
					return res.send({"status": "error", "message": "missing bikeid"});
			}else {
				connection.query('INSERT INTO coordenadas (rota, latitude, longitude) VALUES(\''+req.body.routeid+'\' ,\''+req.body.latitude+'\',\''+req.body.longitude+'\');', function(err, rows, fields) {
									if (err){
										if (err.code === 'PROTOCOL_SEQUENCE_TIMEOUT') {
											node.exit();
										}
										res.status(203).send("Wrong query");
									}else {
										res.status(200).send('OK');
										console.log(rows);
									}
				});
			}
	});

}

module.exports = appRouter;
