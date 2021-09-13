# 第一步：拉取代码
git clone 'http://gitlab.kaikeba.work/root/jingnan_parent_one.git'

# 第二步：单元测试
mvn clean test -pl jingnan_web -am

# 第三步：打包
mvn clean package -Dmaven.test.skip=true  -pl jingnan_web -am

# 第四步：移动到发布服务器的代码仓库
## 移动服务jar包
mkdir -p /usr/local/src/jingnan_web/1.1-SNAPSHOT/
cp ./jingnan_web/target/jingnan_web-1.1-SNAPSHOT.jar /usr/local/src/jingnan_web/1.1-SNAPSHOT/
## 移动服务startup脚本
cp ./jingnan_web/target/classes/startup.sh /usr/local/src/jingnan_web/1.1-SNAPSHOT/
## 移动服务stop脚本
cp ./jingnan_web/target/classes/stop.sh /usr/local/src/jingnan_web/1.1-SNAPSHOT/

# 第五步：发布到目标服务器部署目录
## 清空目标服务器部署最新目录
ssh -p 22 root@10.0.49.5 'rm -rf /usr/local/src/jingnan_web/latest/*'
## 发布服务jar包
scp -P 22 /usr/local/src/jingnan_web/1.1-SNAPSHOT/jingnan_web-1.1-SNAPSHOT.jar root@10.0.49.5:/usr/local/src/jingnan_web/latest/
## 发布服务startup脚本
scp -P 22 /usr/local/src/jingnan_web/1.1-SNAPSHOT/startup.sh root@10.0.49.5:/usr/local/src/jingnan_web/latest/
## 发布服务stop脚本
scp -P 22 /usr/local/src/jingnan_web/1.1-SNAPSHOT/stop.sh root@10.0.49.5:/usr/local/src/jingnan_web/latest/

# 第六步：启动发布服务
## 配置服务stop脚本执行权限
ssh -p 22 root@10.0.49.5 'chmod 755 /usr/local/src/jingnan_web/latest/stop.sh'
## 停止目标服务器之前的服务
ssh -p 22 root@10.0.49.5 '/usr/local/src/jingnan_web/latest/stop.sh'
## 配置服务startup脚本执行权限
ssh -p 22 root@10.0.49.5 'chmod 755 /usr/local/src/jingnan_web/latest/startup.sh'
## 启动目标服务器部署服务
ssh -p 22 root@10.0.49.5 '/usr/local/src/jingnan_web/latest/startup.sh'
