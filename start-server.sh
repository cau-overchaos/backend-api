gradle build  --debug -x test
cd build/libs
nohup java -jar api-0.0.1-SNAPSHOT.jar &
