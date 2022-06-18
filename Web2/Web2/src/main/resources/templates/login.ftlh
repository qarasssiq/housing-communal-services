<#import "parts/common.ftlh" as c>

<@c.page "Login">
    <div class="col-lg-4 offset-4 text-center loginWindow">
        <form action="/login" method="post">
            <div class="row">
                <h1>Авторизация</h1>
                <div style="margin-top: 20px;"><label style="margin-left: 25px">Имя : <input type="text" name="username"/></label></div>
                <div style="margin-top: 20px;"><label>Пароль : <input type="password" name="password"/></label></div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div style="margin-top: 20px;">
                    <input type="submit" value="Войти" style="width: 100px"/>
                    <a href="/registration" class="registration">Зарегистрироваться</a>
                </div>
            </div>
        </form>
    </div>
</@c.page>