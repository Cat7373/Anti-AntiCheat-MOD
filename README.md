# Anti-AntiCheat-MOD  
破解国内某些MC服务器使用 AntiCheat 这个 MOD 来防止客户端修改 MOD 的限制。  

#使用要求:  
1. 带Forge的MC客户端，且版本为1.7.x。  

#快速入门：  
1. 关闭游戏并删除 mods 中的 AntiCheat。  
2. 下载本 MOD 并放置到客户端的 mods 文件夹中。  
3. 打开一次游戏，进入服务器后关闭游戏。  
4. 现在你可以自由的修改客户端里的 MOD 了。  

#其他说明：  
1. 如果没有找到 AntiCheat，请检查 version 中的 json ，应该藏在这里面，删掉即可。  
2. 你可以随时修改 config/antiantiCheat.cfg 来更换发送给服务器的MD5列表，修改后即时生效，无需重启客户端。  
3. 你可以删除掉配置文件中大部分MD5，只保留一个不常变动的MOD，免得每次服务器升级都得修改。  

#配置文件说明：  
从S:MD5List这一行下面开始，每一行是一个MOD的MD5，可以参考后面的附加信息来确定是哪个MOD的。  

#TODO：  
1. 创建一个Wiki。    
