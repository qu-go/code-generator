<html>
<head>
    <title>Welcome!</title>
</head>
<body>
    <ul>
        <#list menuItems as item >
            <li>
                <a href="${item.url}"> ${item.title}</a>
            </li>
        </#list>

    </ul>

    <h1>分支和判空</h1>
    <h2>
        <#if user =="xiaozhang">
            我是校长
        <#else>
            我是其他人
        </#if>
    </h2>

    <h1>${model!"用户为空"}</h1>
    <#if user??>
        存在用户
        <#else >
        用户不存在
    </#if>
    <footer>
        ${currentYear} 代码生成器
    </footer>

    宏定义
    <#macro card username>
        -----------
        ${username}
        -----------
    </#macro>

    <@card username="xiaoming"/>
</body>
</html>