# 1.安装

下载sdk：<a href="https://golang.google.cn/dl/">https://golang.google.cn/dl/</a>

将bin目录加入环境变量

go run编译并直接运行程序，不产生可执行文件，运行速度慢。

go build会产生exe文件，运行速度快。

gofmt输出格式化的文件。

gofmt -w将格式化的文件重新写入。

GOROOT/src：该目录保存了Go标准库代码。
GOPATH/src：该目录保存了应用自身的代码和第三方依赖的代码。go get和go install命令会下载go代码到GOPATH。

# 2.数据类型

## 2.1整数类型

有符号整数int8、int16、int32、int64

无符号整数uint8、uint16、uint32、uint64

```go
package main

import (
	"fmt"
	"unsafe"
)

func main() {
	var n = 100
	fmt.Printf("n的数据类型是%T,n占用的字节是%d", n, unsafe.Sizeof(n))
}
```

## 2.2浮点类型

float32单精度，float64双精度

```go
package main

import "fmt"

func main() {
	var a float32
	var b float64 = 3.1415e2
	var c float32
	fmt.Scanf("%f", &a)
	//吸收回车
	fmt.Scanln()
	fmt.Scanf("%f", &c)
	fmt.Printf("%f,%f,%f\n", a, b, c)
	fmt.Println(a)
}
```

## 2.3字符型

rune：与int32一样，表示一个unicode码

byte：与uint8等价，存储字符

## 2.4布尔型

bool两个值true，false

## 2.5字符串

string，字符串一旦赋值便不可修改

双引号，会识别转义字符

反引号，以字符串的原生形式输出

```go
package main

import "fmt"

func main() {
	var c string = "hello\n"
	var d string = `world\n`
	fmt.Printf("%s%s", c, d)
}
```

拼接字符串

```go
package main

import (
	"bytes"
	"fmt"
)

func main() {
	var buffer bytes.Buffer
	buffer.WriteString("lc")
	buffer.WriteString("dsb")
	fmt.Printf("buffer.String():%v\n", buffer.String())
}
```

切片

```go
package main

import "fmt"

func main() {
	str := "hello,world"
	n := 2
	m := 4
	fmt.Printf("str[n]:%c\n", str[n])
	//截取下标从n到m-1的字符串
	fmt.Printf("str[n:m]:%s\n", str[n:m])
	//截取下标从n到len(s)-1的字符串
	fmt.Printf("str[n:]:%s\n", str[n:])
	//截取下标从0到m-1的字符串
	fmt.Printf("str[:m]:%s\n", str[:m])
}
```

# 3.格式化输出

```go
# 定义示例类型和变量
type Human struct {
    Name string
}
 
var people = Human{Name:"zhangsan"}
```

## 3.1普通占位符

1. 占位符     说明                           举例                   输出
2. %v      相应值的默认格式。            Printf("%v", people)   {zhangsan}，
3. %+v     打印结构体时，会添加字段名     Printf("%+v", people)  {Name:zhangsan}
4. %#v     相应值的Go语法表示            Printf("#v", people)   main.Human{Name:"zhangsan"}
5. %T      相应值的类型的Go语法表示       Printf("%T", people)   main.Human
6. %%      字面上的百分号，并非值的占位符  Printf("%%")            %

## 3.2布尔占位符

1. 占位符       说明                举例                     输出
2. %t          true 或 false。     Printf("%t", true)       true

## 3.3整数占位符

1. 占位符     说明                                  举例                       输出
2. %b      二进制表示                             Printf("%b", 5)             101
3. %c      相应Unicode码点所表示的字符              Printf("%c", 0x4E2D)        中
4. %d      十进制表示                             Printf("%d", 0x12)          18
5. %o      八进制表示                             Printf("%d", 10)            12
6. %q      单引号围绕的字符字面值，由Go语法安全地转义 Printf("%q", 0x4E2D)        '中'
7. %x      十六进制表示，字母形式为小写 a-f         Printf("%x", 13)             d
8. %X      十六进制表示，字母形式为大写 A-F         Printf("%x", 13)             D
9. %U      Unicode格式：U+1234，等同于 "U+%04X"   Printf("%U", 0x4E2D)         U+4E2D

## 3.4浮点数和复数的组成部分（实部和虚部）

1. 占位符     说明                              举例            输出
2. %b      无小数部分的，指数为二的幂的科学计数法，
3. ​        与 strconv.FormatFloat 的 'b' 转换格式一致。例如 -123456p-78
4. %e      科学计数法，例如 -1234.456e+78        Printf("%e", 10.2)     1.020000e+01
5. %E      科学计数法，例如 -1234.456E+78        Printf("%e", 10.2)     1.020000E+01
6. %f      有小数点而无指数，例如 123.456        Printf("%f", 10.2)     10.200000
7. %g      根据情况选择 %e 或 %f 以产生更紧凑的（无末尾的0）输出 Printf("%g", 10.20)   10.2
8. %G      根据情况选择 %E 或 %f 以产生更紧凑的（无末尾的0）输出 Printf("%G", 10.20+2i) (10.2+2i)

## 3.5字符串与字节切片

1. 占位符     说明                              举例                           输出
2. %s      输出字符串表示（string类型或[]byte)   Printf("%s", []byte("Go语言"))  Go语言
3. %q      双引号围绕的字符串，由Go语法安全地转义  Printf("%q", "Go语言")         "Go语言"
4. %x      十六进制，小写字母，每字节两个字符      Printf("%x", "golang")         676f6c616e67
5. %X      十六进制，大写字母，每字节两个字符      Printf("%X", "golang")         676F6C616E67

## 3.6指针

1. 占位符         说明                      举例                             输出
2. %p      十六进制表示，前缀 0x          Printf("%p", &people)             0x4f57f0

# 4.数组

```go
package main

import "fmt"

func main() {
	var a = [...]int{1, 2, 3}
	var b [3]string
	fmt.Printf("a:%T\n", a)
	fmt.Printf("b:%T\n", b)
	fmt.Printf("a:%v\n", a)
	fmt.Printf("b:%v\n", b)
}

func f() {
	var a = [5]int{1, 2, 3, 4, 5}
	for i, v := range a {
		fmt.Printf("i:%d,v:%d\n", i, v)
	}
}
```

# 5.切片

```go
package main

import "fmt"

func main() {
	f1()
}

func f1() {
	//var slice = []int{1,2,3}
	var slice []int = make([]int, 1)
	fmt.Printf("%p\n", slice)
	fmt.Printf("%p\n", &slice[0])
	slice = append(slice, 1, 2, 3)
	fmt.Printf("%p\n", slice)
	fmt.Printf("%p\n", &slice[0])
	for i, v := range slice {
		fmt.Printf("i:%d,v:%d\n", i, v)
	}
}
```

# 6.map

```go
package main

import "fmt"

func main() {
	test()
}

func test() {
	//var m1 map[string]int = make(map[string]int)
	m1 := make(map[string]int)
	m1["key1"] = 1
	m1["key2"] = 2
	m1["key3"] = 3
	for k, v := range m1 {
		fmt.Printf("k=%v,v=%v\n", k, v)
	}
	delete(m1, "key3")
	//判断key是否存在
	value, ok := m1["key1"]
	fmt.Printf("%v,%v\n", value, ok)
	value, ok = m1["key3"]
	fmt.Printf("%v,%v\n", value, ok)
}
```

# 7.函数

多返回值

```go
package main

import "fmt"

func main() {
	ret, ret2 := add(1, 2)
	fmt.Printf("%d,%d\n", ret, ret2)
}
func add(a int, b int) (int, int) {
	return a + b, a - b
}
```

可变参数

```go
package main

import "fmt"

func main() {
	f2("name", 1, 2, 3, 4, 5, 6)
}

func f2(name string, args ...int) {
	fmt.Printf("name:%v\n", name)
	for _, value := range args {
		fmt.Printf("value:%v\n", value)
	}
}
```

**闭包**：定义在函数内部的函数

```go
package main

import (
	"fmt"
)

func main() {
    //在f的生命周期内，x一直有效
	var f = add()
	fmt.Printf("%v\n", f(10))
}

func add() func(int) int {
    var x int
	return func(y int) int {
        x = x+y
		return y
	}
}
```

# 8.defer语句

特性

(1)用于注册延迟调用

(2)这些调用直到return前才被执行

(3)多个defer语句，按先进后出的方式执行

(4)defer语句的变量，在defer声明时就决定了

用途：(1)关闭文件句柄、(2)锁资源释放、(3)数据库连接释放

```go
package main

import (
	"fmt"
)

func main() {
	fmt.Printf("start!\n")
	defer fmt.Printf("defer1\n")
	defer fmt.Printf("defer2\n")
	defer fmt.Printf("defer3\n")
	fmt.Printf("end!\n")
}
```

# 9.init函数

(1)先于main函数自动执行

(2)没有输入参数和返回值

(3)包的每个源文件也可以有多个init函数

(4)同一个包的init执行顺序没有明确定义，不同包的init函数安装包导入的依赖关系决定执行顺序

# 10.指针

```go
package main

import (
	"fmt"
)

func main() {
	a := []int{1, 2, 3}
	var b *[]int
	var c []int
	b = &a
	c = a
	fmt.Printf("a:%p\n", a)
	fmt.Printf("b:%p\n", b)
	fmt.Printf("&a:%p\n", &a)
	fmt.Printf("&a[0]:%p\n\n", &a[0])
	a = append(a, 4, 5, 6)
	fmt.Printf("a:%p\n", a)
	fmt.Printf("b:%p\n", b)
	fmt.Printf("&a:%p\n", &a)
	fmt.Printf("&a[0]:%p\n", &a[0])
	fmt.Println(a)
	fmt.Println(*b)
	fmt.Println(c)
}
```

![1](imgs\1.png)

# 11.自定义类型

```go
//类型定义
type NewType Type
//类型别名
type NewName = Type
//结构体
type Node struct {
	value int
	next *Node
}
```

# 12.方法

一种特殊的函数，定义于struct之上(与struct关联、绑定)，被称为struct的接受者

```go
package main

import "fmt"

type Node struct {
	value int
	next  *Node
}

func main() {
	node := Node{next: nil, value: 1}
	node.getValue()
	fmt.Printf("%d\n", node.value)
	node.get()
	fmt.Printf("%d\n", node.value)
}

func (per Node) getValue() int {
	per.value = 3
	return per.value
}

func (per *Node) get() int {
	//per = new(Node)
	per.value = 2
	return per.value
}
```

# 13.接口

```go
package main

import "fmt"

type USB interface {
	read()
	write()
}

type computer struct {
	name string
}

type phone struct {
	name string
}

func (c computer) read() {
	fmt.Printf("computer %s read!\n", c.name)
}

func (c computer) write() {
	fmt.Printf("computer %s write!\n", c.name)
}

func (p phone) read() {
	fmt.Printf("computer %s read!\n", p.name)
}

func (p phone) write() {
	fmt.Printf("computer %s write!\n", p.name)
}

func main() {
	var c USB
	var p USB
	c = computer{name: "c"}
	p = phone{name: "p"}
	c.read()
	c.write()
	p.read()
	p.write()
}
```

# 14.go mod

```shell
go env -w GO111MODULE=on
go env -w GO111MODULE=off
go env -w GO111MODULE=auto
go env -w GOPROXY=https://goproxy.cn,direct
//初始化模块
go mod init <项目模块名称>
//处理依赖关系
go mod tidy
//将依赖包复制到项目下的vendor目录
go mod vendor
//显示依赖关系
go list -m all
//显示详细依赖关系
go list -m -json all
//下载依赖
go mod download [path@version]
```

<a href="https://zhuanlan.zhihu.com/p/482014524">go mod使用</a>

新建文件加

go mod init <项目模块名称>

编写代码

go mod tidy

运行

# 15.并发编程

## 15.1协程

Goroutines是并发运行的函数

```go
package main

import (
	"fmt"
	"time"
)

func showMsg(msg string) {
	for i := 0; i < 3; i++ {
		fmt.Printf("msg:%v\n", msg)
		time.Sleep(time.Millisecond * 100)
	}
}
func main() {
    //创建一个协程
	go showMsg("java")
	go showMsg("go")
	time.Sleep(time.Millisecond * 500)
}
```

## 15.2通道

用于在goroutine之间共享数据，需要在声明通道时指定**数据类型**，在任何给定时间只有一个goroutine可以访问数据项，因此不会发生数据竞争

```go
//无缓存int通道，无缓存的通道只有在有人接收值的时候才能发送值
Unbuffered := make(chan int)
//有缓存int通道
buffered := make(chan int, 10)
```

sync.WaitGroup

```go
package main

import (
	"fmt"
	"sync"
)

var wg sync.WaitGroup

func recv(c chan int) {
	fmt.Printf("recv\n")
	for value := range c {
		fmt.Printf("value:%v\n", value)
	}
	defer wg.Done()
}

func send(c chan int) {
	defer wg.Done()
	//不关闭会产生死锁
	defer close(c)
	for i := 0; i < 5; i++ {
		c <- i
		fmt.Printf("send:%v\n", i)
	}
}
func main() {
	ch := make(chan int)
	go recv(ch)
	wg.Add(1)
	go send(ch)
	wg.Add(1)
	wg.Wait()
	//time.Sleep(time.Millisecond * 500)
}
```

## 15.3runtime

```go
package main

import (
	"fmt"
	"runtime"
	"sync"
)

var wg sync.WaitGroup

func show(str string) {
	defer wg.Done()
	for i := 0; i < 5; i++ {
		if i >= 4 {
			//退出协程
			runtime.Goexit()
		}
		fmt.Printf("%s\n", str)
	}
}

func main() {
	fmt.Printf("当前CPU核心数:%v\n", runtime.NumCPU())
	//设置最大核心数
	//runtime.GOMAXPROCS(1)
	go show("java")
    wg.Add(1)
	for i := 0; i < 2; i++ {
		//有权执行任务，让给其他子协程
		runtime.Gosched()
		fmt.Printf("golang\n")
	}
	wg.Wait()
}
```

## 15.4Mutex

锁

```go
package main

import (
	"fmt"
	"sync"
	"time"
)

var i int = 100

var wg sync.WaitGroup

var lock sync.Mutex

func add() {
	defer wg.Done()
    //不加锁结果可能出错
	lock.Lock()
	i += 1
	fmt.Printf("i++:%v\n", i)
	time.Sleep(time.Millisecond * 3)
	lock.Unlock()
}

func sub() {
	defer wg.Done()
	lock.Lock()
	i -= 1
	fmt.Printf("i--:%v\n", i)
	time.Sleep(time.Millisecond * 2)
	lock.Unlock()
}

func main() {
	for i := 0; i < 100; i++ {
		wg.Add(1)
		go add()
		wg.Add(1)
		go sub()
	}
	wg.Wait()
	fmt.Printf("end:%v\n", i)
}
```

## 15.5select

实现异步io

```go
package main

import (
	"fmt"
	"sync"
)

var wg sync.WaitGroup

func send(chanInt chan int, chanStr chan string) {
	chanInt <- 100
	chanStr <- "hello"
	defer wg.Done()
	defer close(chanInt)
	defer close(chanStr)
}
func recv(chanInt chan int, chanStr chan string) {
	defer wg.Done()
	for i := 0; i < 2; {
		select {
		case r := <-chanInt:
			fmt.Printf("chanInt:%v\n", r)
			i++
		case r := <-chanStr:
			fmt.Printf("chanStr:%v\n", r)
			i++
		default:
			fmt.Printf("default\n")
		}
	}
}
func main() {
	chanInt := make(chan int)
	chanStr := make(chan string)
	go recv(chanInt, chanStr)
	wg.Add(1)
	go send(chanInt, chanStr)
	wg.Add(1)
	wg.Wait()
}
```



