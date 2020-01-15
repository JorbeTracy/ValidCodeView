# ValidCodeView
用于短信验证场景，支持设置验证码长度，文字颜色，下划线长度等。同时提供了掩码/明码两种显示模式。

&nbsp;
![screenshot_1](/image/screenshot.png)

&nbsp;

## 如何集成
#### Gradle：
```
implementation 'com.jorbe:ValidCodeView:1.0.1'
```

#### Maven：
```
<dependency>
	<groupId>com.jorbe</groupId>
	<artifactId>ValidCodeView</artifactId>
	<version>1.0.1</version>
	<type>pom</type>
</dependency>
```

#### 或者：
> 下载本demo，将 `validcodeviewlib` 库添加到项目的依赖中(module dependency)。

&nbsp;

## 功能介绍
属性 | 描述
|-|-|
code_count | 验证码长度
text_size | 字体大小
text_color | 文字颜色
line_color | 下划线颜色
dot_color | 掩码模式文字颜色
text_mode | 显示模式，目前有普通(type_normal)和掩码(type_psd)两种模式