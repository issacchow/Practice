# top
> top -h -p {pid}
[  打开Data列: 按f建，通过空格选择显示，方向右键控制列的顺序 ]

输出一次指定jvm进程的所有线程信息
> top -p 15234 -H -b -n 1


# 查看内存示例数量, 并按指定列排序
jmap -histor:live {pid} | sort -n -b -k {列的索引位置,从1开始,一般2或3} | less

# 查看gc过程
# jstat -gcutil -h 20 -t {jvmid} 1s
-h 参数用于控制每输出多少行记录后输出一个列头
-t 用于显示递增时间

# 查看各个内存空间占用情况
jmap -heap {pid}

#MySql数据库

1. 查看运行状态
> show status;
or
> show status like 'innodb'
or
> 接使用Mywork Bentch

2. show table status like '表';

3. show processlist;

