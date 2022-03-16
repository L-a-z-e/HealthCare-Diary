const   cookieParser = require('cookie-parser');
const   session = require('express-session');
const   bodyParser = require('body-parser');
const   express = require('express');
const   app = express();
const   createError = require('http-errors');
const   path = require('path');
const   PORT = 65000;

// Configuration
app.set('views', path.join(__dirname, 'views'));  // views경로 설정
app.set('view engine', 'ejs');                    // view엔진 지정
app.use(express.static(path.join(__dirname, 'public')));   // public설정
app.use('/css', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'css')));   // css설정
app.use('/js', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'js')));   // js설정
app.use('/img', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'img')));
app.use('/assets', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'assets')));
app.use('/fonts', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'fonts')));
app.use('/contactform', express.static(path.join(__dirname, 'public', 'NiceAdmin', 'contactform')));
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.json());
app.use(session({ key: 'sid',
                  secret: 'secret key',  // 세션id 암호화할때 사용
                  resave: false,         // 접속할때마다 id부여금지
                  saveUninitialized: true })); // 세션id사용전에는 발급금지

// Routes

require('./routes/routes.js')(app);


app.listen(PORT, function () {
    console.log('서버실행: http://localhost:' + PORT);
});