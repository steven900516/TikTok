# User类
> 大User，包含UserDetail和UserCommon，可支持拓展

* Redis 存储
  * key : ` [tt.user]-[storage]-uid-User`
  * value : User(Object) 



# UserCommon类
> UserCommon，包含用户通用基本信息，UID，DID和抖音号(ttAcount)

* Redis 存储
    * key : ` [tt.user]-[storage]-uid-UserCommon`
    * value : UserCommon(Object) 



# UserDetail类
> UserDetail，包含用户详细信息：名字，简介，学校，抖音号……

* Redis 存储
  * key : ` [tt.user]-[storage]-uid-UserDetail`
  * value : UserCommon(Object) 


---

* Redis 修改记录存储
  * 抖音号修改记录（180天可修改一次）
    * key : ` [tt.user]-[record]-{{uid}}-UserDetail-ttAcount`
    * value :  "1"
  * 姓名修改记录 （30天可修改一次）
    * key : ` [tt.user]-[record]-{{uid}}-UserDetail-name`
    * value :  "1"
  * 抖音号检验是否存在
    * key ： ` [tt.user]-[record]-{{ttAcount}}-ttAccount`
    * value :  "1"