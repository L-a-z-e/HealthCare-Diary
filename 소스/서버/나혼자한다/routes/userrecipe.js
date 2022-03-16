const   express = require('express');
const   mysql = require('mysql');
const   bodyParser = require('body-parser');
const   url = require('url');
const   session = require('express-session');
const   methodOverride = require('method-override');
const   multer = require('multer');
const   async = require('async');
const   router = express.Router();
const   fs = require('fs');
var path = require('path');

const   upload = multer({dest: __dirname + '/../public/images/uploads/exercise'});
const   db = mysql.createConnection({
    host: 'localhost',        // DB서버 IP주소
    port: 3306,               // DB서버 Port주소
    user: 'root',            // DB접속 아이디
    password: '1234',  // DB암호
    database: 'project'         //사용할 DB명
});

router.use(methodOverride('_method'));
router.use(bodyParser.urlencoded({ extended: false }));

// 카테고리별 메뉴리스트를 출력합니다.
const Categorylist = (req, res) => {
  var MenuListArray = new Array();
  const  query = url.parse(req.url, true).query;
  
  console.log(query.category);
  console.log('카테고리별 리스트 요청보냄');
  console.log('hi');
  let sql_str = 'SELECT * from menu where category = ?';

  console.log('GET 데이터 받음');
  
    db.query(sql_str, [query.category], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            console.log('메뉴리스트 데이터 보냄');
            console.log(results);
            results.forEach((item,index)=>{                         
                 MenuListArray.push(item);                             
            });             
            res.json(MenuListArray);
            MenuListArray = [];                            
        }                     
  });
};



// 운동 리스트를 출력합니다.
const Exerciselist = (req, res) => {
    var ExerciseListArray = new Array();
    const  query = url.parse(req.url, true).query;
    console.log(query.category);
    console.log('부위별 리스트 요청보냄');
if(query.part==1){
    let sql_str = 'SELECT * from exercise order by name';
  
    console.log('GET 데이터 받음');
    
      db.query(sql_str,(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              console.log('운동리스트 데이터 보냄');
              console.log(results);
              results.forEach((item,index)=>{                         
                ExerciseListArray.push(item);                             
              });             
              res.json(ExerciseListArray);
              ExerciseListArray = [];                            
          }                     
    });
}

else{  
      let sql_str = 'SELECT * from exercise where part = ? order by name';
  
    console.log('GET 데이터 받음');
    
      db.query(sql_str,[query.part],(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              console.log('운동리스트 데이터 보냄');
              console.log(results);
              results.forEach((item,index)=>{                         
                ExerciseListArray.push(item);                             
              });             
              res.json(ExerciseListArray);
              ExerciseListArray = [];                            
          }                     
    });
}
  };
  
  
// 난이도별 메뉴리스트를 출력합니다.
const Levellist = (req,res) => {
    var LevelListArray = new Array();
    const  query = url.parse(req.url, true).query;
    console.log(query.difficulty);
    console.log('난이도별 리스트 요청보냄');
    let sql_str = 'SELECT * from menu where difficulty = ?';
    console.log('GET 데이터 받음'); 
    db.query(sql_str, [query.difficulty], (error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              console.log('메뉴리스트 데이터 보냄');
              console.log(results);
              results.forEach((item,index)=>{                         
                LevelListArray.push(item);                             
              });             
              res.json(LevelListArray);
              LevelListArray = [];                            
          }                     
    });
};
// 즐겨찾기 목록을 출력합니다.
const Favoritelist = (req,res) => {
    let favorites;
    const  query = url.parse(req.url, true).query;
    console.log(query.userid);
    console.log('즐겨찾기 리스트 요청보냄');
    let sql_str1 = 'SELECT favorite from users where userid = ?';
    let sql_str2 = 'SELECT * from menu where';
    console.log('GET 데이터 받음'); 
    db.query(sql_str1, [query.userid], (error, results1) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
                console.log(results1[0].favorite);
                if(results1[0].favorite == "" || results1[0].favorite == null){
                    sql_str2 = sql_str2 + ' menunum = -1';
                }else{
                    favorites = results1[0].favorite.split(",");
                    for(let i = 0; i< favorites.length; i++){
                        if(i == favorites.length - 2){
                            sql_str2 = sql_str2 + ' menunum = ' + favorites[i];
                            break;
                        }else
                            sql_str2 = sql_str2 + ' menunum = ' + favorites[i] + ' OR ';
                    }
                }
                db.query(sql_str2, (error, results2) => {
                    if(error){
                        console.log(error);
                        res.end("error");
                    }else{
                        console.log("즐겨찾기 리스트 데이터 보냄");
                        res.json(results2);
                    }
                });
        }               
                              
    });
};
// 사용자의 즐겨찾기 데이터를 보내줍니다.
const UserFavorites = (req, res) => {
    const  query = url.parse(req.url, true).query;
    console.log(query.userid);
    console.log('즐겨찾기 데이터 요청 보냄');
    let sql_str = 'SELECT favorite from users where userid = ?';
    db.query(sql_str, [query.userid], (error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              console.log('즐겨찾기 데이터 보냄');
              console.log(results);          
              res.json(results);                      
          }                     
    });
};
// 사용자 즐겨찾기 데이터를 갱신합니다.
const UpdateFavorite = (req,res) => {
    let body = req.body;
    console.log(body);
    let sql_str = 'Update users Set favorite = ? where userid = ?';
    db.query(sql_str, [body.favorite, body.userid], (error) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            console.log('즐겨찾기 갱신 요청 보냄');    
            res.end("OK");                      
        }                     
  });
};
// 레시피 게시판리스트를 출력합니다.
const MyRecipelist = (req,res) => {
    var MyRecipeListArray = new Array();
    console.log('레시피게시판 리스트 요청보냄');
    let sql_str = 'SELECT * from menu where uploaduser != "admin"';
    console.log('GET 데이터 받음'); 
    db.query(sql_str, (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            console.log('메뉴리스트 데이터 보냄');
            console.log(results);
            results.forEach((item,index)=>{                         
            MyRecipeListArray.push(item);                             
            });             
            res.json(MyRecipeListArray);
            MyRecipeListArray = [];                            
        }                     
    });
 
};
// 각 메뉴의 레시피상세정보를 출력합니다.
const DetailRecipe = (req,res) => {
    var RecipeDetailArray = new Array();
    const  query = url.parse(req.url, true).query;
    console.log(query.category);
    console.log('카테고리별 리스트 요청보냄');
    let sql_str = 'SELECT * from recipe where menunum = ?';
  
    console.log('GET 데이터 받음');
    
      db.query(sql_str, [query.menunum], (error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              console.log('레시피상세정보 데이터 보냄');
              console.log(results);
              results.forEach((item,index)=>{                         
                RecipeDetailArray.push(item);                             
              });             
              res.json(RecipeDetailArray);
              RecipeDetailArray = [];                            
          }                     
    });
};

const MeterialRecipe = (req, res) => {
    let MenuListArray = new Array();
    let meterials;
    let sql_str1 = 'SELECT * from recipe where ';
    let sql_str2 = 'SELECT * from menu where ';
    const  query = url.parse(req.url, true).query;
    meterials = query.meterial.split(",");
    console.log(meterials.length);
    for(let i = 0; i < meterials.length; i++){
        if(i == meterials.length - 1){
            sql_str1 += "meterial LIKE '%" + meterials[i] + "%'";
        }else{
            sql_str1 += "meterial LIKE '%" + meterials[i] + "%'" + ' AND ';
        }
    }
    console.log(sql_str1);
    db.query(sql_str1 , (error, results1) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            console.log(results1);
            console.log(results1.length);
            if(results1.length == 0){
                res.end("empty");
            }else{
                for(let i = 0; i < results1.length; i++){
                    if(i == results1.length - 1){
                        sql_str2 += 'menunum = ' + results1[i].menunum;
                    }else{
                        sql_str2 += 'menunum = ' + results1[i].menunum + ' OR ';
                    }
                }      
                console.log(sql_str2);
                db.query(sql_str2, (error, results2) => {
                    if(error){
                        console.log(error);
                        res.end("error");
                    }else{
                        console.log('재료레시피 메뉴데이터 보냄');
                        console.log(results2);
                        results2.forEach((item,index)=>{                                                         
                            MenuListArray.push(item);                             
                        });             
                        res.json(MenuListArray);
                        MenuListArray = [];      
                    }
                }); //db.qeury() 
            }               
        }                             
    });
};

const Image1 = (req,res) => {
    let    prodimage = '/images/uploads/exercise/'; // 상품이미지 저장디렉터리
    let    picfile = req.file;
    
    prodimage = prodimage + picfile.filename;
    console.log(prodimage);
    let sql_str = 'UPDATE exercisediary SET image1 = ? where num = LAST_INSERT_ID()';
    db.query(sql_str, [prodimage], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            res.json({'response':"ok"});
        }                     
    });
    
};

const Image2 = (req,res) => {
    let    prodimage = '/images/uploads/exercise/'; // 상품이미지 저장디렉터리
    let    picfile = req.file;
    console.log(prodimage);
    prodimage = prodimage + picfile.filename;
    let sql_str = 'UPDATE exercisediary SET image2 = ? where num = LAST_INSERT_ID()';
    db.query(sql_str, [prodimage], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            res.json({'response':"ok"});
        }                     
    });
    
};
const BodyImage = (req,res) => {
    let    prodimage = '/images/uploads/exercise/'; // 상품이미지 저장디렉터리
    let    picfile = req.file;
    console.log(prodimage);
    prodimage = prodimage + picfile.filename;
    let sql_str = 'UPDATE body SET image = ? where num = LAST_INSERT_ID()';
    db.query(sql_str, [prodimage], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            res.json({'response':"ok"});
        }                     
    });
    
};



const Insertdiary = (req,res) => {
    
    let sql_str = 'INSERT INTO exercisediary(userid,year,month,day,exercisetime,exercisekcal,exercisememo)VALUES(?,?,?,?,?,?,?)';
    let body = req.body;
    console.log(req.body);
    console.log('POST 데이터 받음');
    db.query(sql_str, [body.userid,body.year,body.month,body.day,body.exercisetime,body.exercisekcal,body.exercisememo], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            res.end("ok");
        }                     
    });
};
const Insertbody = (req,res) => {
    let body = req.body;
    console.log(req.body);
    console.log('POST 데이터 받음');
   
    if(body.action==1){
        let sql_str = 'INSERT INTO body(userid,year,month,day)VALUES(?,?,?,?)';
     
    db.query(sql_str, [body.userid,body.year,body.month,body.day], (error, results) => {
        if (error) {     
            console.log(error);
            res.end("error");
        } else {
            res.end("ok");
        }                     
    });}
    else{
        res.end("ok");
    }
};

const Getdiary = (req, res) => {
    var Exercisediary = new Array();
    const  query = url.parse(req.url, true).query;
    
    let sql_str = 'SELECT * from exercisediary where userid = ? AND year = ? AND month = ? AND day = ?';
  
    console.log('GET 데이터 받음');
    
      db.query(sql_str,[query.userid,query.year,query.month,query.day],(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              
              
              results.forEach((item,index)=>{                         
                Exercisediary.push(item);                             
              });             
              console.log(Exercisediary);
              res.json(Exercisediary);
              Exercisediary = [];                            
          }                     
    });
  };

  const Getbody = (req, res) => {
    var Exercisediary = new Array();
    const  query = url.parse(req.url, true).query;
    if(query.day==0000){
        let sql_str = 'SELECT * from body where userid = ? order by num LIMIT 30';
  
        console.log('GET 데이터 받음');
        
          db.query(sql_str,[query.userid,query.year,query.month,query.day],(error, results) => {
              if (error) {     
                  console.log(error);
                  res.end("error");
              } else {
                  
                  
                  results.forEach((item,index)=>{                         
                    Exercisediary.push(item);                             
                  });             
                  console.log(Exercisediary);
                  res.json(Exercisediary);
                  Exercisediary = [];                            
              }                     
        });

    }else{
    let sql_str = 'SELECT image from body where userid = ? AND year = ? AND month = ? AND day = ?';
  
    console.log('GET 데이터 받음');
    
      db.query(sql_str,[query.userid,query.year,query.month,query.day],(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              
              
              results.forEach((item,index)=>{                         
                Exercisediary.push(item);                             
              });             
              console.log(Exercisediary);
              res.json(Exercisediary);
              Exercisediary = [];                            
          }                     
    });}
  };
  
  const Getchart = (req, res) => {
    var Exercisechart = new Array();
    const  query = url.parse(req.url, true).query;
    
    let sql_str = 'SELECT day,SUM(exercisetime) as totaltime,SUM(exercisekcal) as totalkcal FROM exercisediary where userid = ? AND year = ? AND month = ? group by day order by day';
    
    console.log(query.month);
       console.log(query.month+'월 차트');
      db.query(sql_str,[query.userid,query.year,query.month],(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
              
              console.log(results);
              results.forEach((item,index)=>{                         
                Exercisechart.push(item);                             
              });             
              console.log(Exercisechart);
              res.json(Exercisechart);
              Exercisechart = [];                            
          }                     
    });
  };
  const GroupList = (req, res) => {
    var Exercisechart = new Array();
    const  query = url.parse(req.url, true).query;
    let sql_str1 = 'SELECT userid FROM users WHERE groupname = ?';
    let sql_str2 = 'SELECT userid,SUM(exercisetime) as totaltime,SUM(exercisekcal) as totalkcal FROM exercisediary where year = ? AND month = ? AND userid in(';
   
    
    console.log(query.month);
      
      db.query(sql_str1,[query.groupname],(error, results) => {
          if (error) {     
              console.log(error);
              res.end("error");
          } else {
            for(let i = 0; i < results.length; i++){
                if(i == results.length - 1){
                    sql_str2 += "'"+results[i].userid+"'" + ') group by userid';
                }else{
                    sql_str2 += "'"+results[i].userid+"'"+',';
                }
            }  
            db.query(sql_str2,[query.year,query.month],(error, results2) => {
                if(error){
                    console.log(error);
                    res.end("error");
                }else{
                    console.log(results2);
              results2.forEach((item,index)=>{                         
                Exercisechart.push(item);                             
              });             
              console.log(Exercisechart);
              res.json(Exercisechart);
              Exercisechart = [];          
                }
            }); //db.qeury() 
                                
          }                     
    });
  };


router.get('/group', GroupList);
router.get('/category', Categorylist);
router.get('/exercise', Exerciselist);
router.get('/difficulty', Levellist);
router.get('/favoritelist', Favoritelist);
router.get('/favorite', UserFavorites);
router.get('/myrecipe',  MyRecipelist);
router.get('/detail',  DetailRecipe);
router.put('/favorite', UpdateFavorite);
router.get('/meterial', MeterialRecipe);
router.post('/image1', upload.single('image'), Image1);
router.post('/image2', upload.single('image'), Image2);
router.post('/bodyimg', upload.single('image'), BodyImage);
router.post('/insertbody',Insertbody);
router.post('/insertdiary',Insertdiary);
router.get('/getdiary',Getdiary);
router.get('/getchart',Getchart);    
router.get('/getbody',Getbody);  
router.get('/',function(req, res) { res.send('respond with a resource 111'); });

module.exports = router;
