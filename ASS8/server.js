var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));


// default route 
app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Test Employee Web API' })
});
// connection configurations 
var dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'ass8_employee'
});

// connect to database dbconn.connect();
dbConn.connect();

app.get('/allemp', function (req, res) {
    dbConn.query('SELECT * FROM employee', function (error, results, fields) {
        if (error) throw error;
        return res.send(results);

    });
});


app.post('/emp', function (req, res) {
    var emp = req.body
    if (!emp) {
        return res.status(400).send({ error: true, messge: 'Please provide employee ' });


    }
    dbConn.query("INSERT INTO employee SET ?", emp, function (error, results, fields) {
        if (error) throw error;
        return res.send(results);
    });
});



app.listen(3000, function () {
    console.log('Node app is running on port 3000');

});

module.exports = app;