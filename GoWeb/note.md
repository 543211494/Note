# 1.操作数据库

```go
package main

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)
type user struct {
	userName   string
	password   string
	createTime string
	manager    int
}

func connect(db **sql.DB) (err error) {
	*db, err = sql.Open("mysql", "root:123456@tcp(127.0.0.1:3306)/query?charset=utf8mb4&parseTime=True")
	if err != nil {
		fmt.Printf("%#v\n", err)
	}
	return err
}
func main() {
	var db *sql.DB
	//defer db.Close()
	connect(&db)
	var u user
	users := make([]user, 0)
	defer db.Close()
	//插入数据
	db.Exec("insert into user (userName, password, email, phone, createTime, manager) VALUES (?,?,?,?,now(),?)", "user002", "123456", "123344@qq.com", "321", 0)
	//单行查询
	db.QueryRow("select userName,password,createTime,manager from user where userName = 'user002'").Scan(&u.userName, &u.password, &u.createTime, &u.manager)
	fmt.Printf("%#v\n", u)
	//删除数据
	db.Exec("delete from user where userName = 'user002'")
	//多行查询
	rows, _ := db.Query("select userName,password,createTime,manager from user")
	for rows.Next() {
		rows.Scan(&u.userName, &u.password, &u.createTime, &u.manager)
		users = append(users, u)
	}
	for i := 0; i < len(users); i++ {
		fmt.Printf("%#v\n", users[i])
	}
}
```

# 2.处理请求

# 2.1搭建服务器

```go
package main

import (
	"fmt"
	"net/http"
)

type MyHandler struct {
}

func (h *MyHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "MyHandler!")
}

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "hello world!")
}

func main() {
	myHandler := MyHandler{}
	http.Handle("/handler", &myHandler)
	http.HandleFunc("/hello", handler)
	//创建路由
	http.ListenAndServe(":8080", nil)
}
```

## 2.2获取请求体和参数

```go
package main

import (
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"io/ioutil"
	"net/http"
)

// 获取参数
func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "请求路径:%#v\n", r.URL.Path)
	fmt.Fprintf(w, "请求参数:%#v\n", r.URL.RawQuery)
	//Header的类型为map[string][]string
	fmt.Fprintf(w, "请求头:%#v\n", r.Header)
	//获取请求体,r.Body.Read后输入流关闭了r.From就获取不到body的参数了
	//body := make([]byte, r.ContentLength)
	//r.Body.Read(body)
	//fmt.Fprintf(w, "请求体内容:%#v\n", string(body))
	//解析表单,调用r.From之前必须调用r.ParseForm(),body和query会一块解析
	//要解析body的参数,Content-Type的值要为application/x-www-form-urlencoded
	r.ParseForm()
	fmt.Printf("%#v\n", r.Form)
	fmt.Printf("%#v\n", r.PostForm)
}

// 文件上传
func upload(w http.ResponseWriter, r *http.Request) {
	//获取普通表单数据
	r.ParseForm()
	fmt.Printf("%#v\n", r.Form)
	fmt.Printf("%#v\n", r.PostForm)
	//获取文件流,第三个返回值是错误对象
	file, header, _ := r.FormFile("photo")
	//读取文件流为[]byte
	b, _ := ioutil.ReadAll(file)
	//把文件保存到指定位置
	_ = ioutil.WriteFile("D:/new.png", b, 0777)
	//输出上传时文件名
	fmt.Println("上传文件名:", header.Filename)
}

type data struct {
	UserName string `json:"userName"`
	Age      int    `json:"age"`
	Gender   bool   `json:"gender"`
}

// 回复客户端
func testResponse(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/form-data")
	res := data{UserName: "李畅", Age: 20, Gender: true}
	j, err := json.Marshal(res)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println(string(j))
	w.Write(j)
}

func main() {
	http.HandleFunc("/hello", handler)
	http.HandleFunc("/upload", upload)
	http.HandleFunc("/res", testResponse)
	//创建路由
	http.ListenAndServe(":8080", nil)
}
```

## 2.3跨越问题

```go
w.Header().Set("Access-Control-Allow-Origin", "*") //允许访问所有域
w.Header().Add("Access-Control-Allow-Headers", "Content-Type") //header的类型
w.Header().Set("content-type", "application/json") //返回数据格式是json
```

