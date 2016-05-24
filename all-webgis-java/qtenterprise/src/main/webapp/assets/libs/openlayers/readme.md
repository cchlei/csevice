# 地图编辑的接口定义

---

---

> 图层字段增加 layer_field_add

>> 请求参数

>>> field 字段名

>>> title 字段别称

>>> type 字段类型

>> 响应参数

>>> result

>>>> "success"表示创成功

>>>> "failt" 或者其他 表示创建失败        此时 "msg"附带错误信息



---

> 图层字段删除 layer_field_del

>> 请求参数

>>> field 字段名称

>> 响应参数 参考result


---

> 图层字段的获取 layer_field_get

>> 请求参数 

>>> layerid(暂定，最终使用使用模型中的字段)

>> 响应参数

>>> rows 字段列表数组

>>> rows[0].field 字段的键名

>>> rows[0].title 字段的别名

>>> rows[0].type  字段类型

---


> 矢量创建 /entgraphics/editentgraphics

>> 请求参数

>>> geom 矢量的几何信息 

>>> mapid 地图id

>>> gtype 点1线2面3 

>> 响应参数 

>>> featureid 矢量id，取模型字段

---


> 矢量属性的保存,现在设定为批量保存 /entgraphics/editentgraphics

>> 请求参数

>>> featureid 矢量id

>>> [属性名：属性值]键值对

>>>...

>> 响应参数 参照“图层字段增加”里面对result的定义

---

> 矢量属性的获取 /entgraphics/getgraphics

>> 请求参数

>>> gid 矢量id

>>> mapid 地图id 

>> 响应

>>> [属性名：属性值]键值对

>> 响应举例

>>> ```json
{
	"featureid":545455,
	"geom":8855455454545454,
	"name":"大雁塔",
	"link":"//baidu.com",
	...
}
```
    

