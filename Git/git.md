查看全局的配置信息可以使用下面的命令行

git config --global --list

设置 username 和 email,最后一个命令的目的是保存用户名和密码

git config --global user.name "<这里写你的名字>"
git config --global user.email "<这里写你的信箱>"
git config --global credential.helper store

把文件添加到缓冲区

git add filename

添加所有文件到缓冲区

git add .

删除文件

git rm filename

git commit -m "提交的说明"

查看git库的状态

git status

## git分支管理

查看分支的情况，前面带*号的就是当前分支

git branch 

创建分支

git branch 分支名

创建分支

git branch 分支名 

切换当前分支到指定分支

git checkout 分支名

创建分支并切换到创建的分支

git checkout  -b 分支名

合并某分支的内容到当前分支

git merge 分支名

删除分支

git branch -d 分支名



关联远程代码仓库

git remote add origin 你的远程库地址



git rm -r --cached .

git add .

git commit -m "study-note"

git push -u origin 分支名



下载指定分支

git clone -b 分支名 仓库地址

git config --global http.sslverify false

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



