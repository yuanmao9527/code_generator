<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的网站</title>
</head>
<body>
<h1>欢迎光临~</h1>
<ul>
    <#-- 循环导航条 -->
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a></li>
    </#list>
</ul>
<#-- 底部版权信息 -->
<footer>
    ${currentYear} yuanmao9527. All rights reserved.
</footer>
</body>
</html>