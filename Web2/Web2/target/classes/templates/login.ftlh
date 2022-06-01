<#import "parts/common.ftlh" as c>

<@c.page "Login">
    <#if message??>
        <#if message = "Пользователь успешно активирован.">
            <script>window.alert("Пользователь успешно активирован.");</script>
        </#if>
        <#if message = "Код активации не найден!">
            <script>window.alert("Код активации не найден!");</script>
        </#if>
    </#if>
    <div class="col-lg-4 offset-4 text-center loginWindow">
        <form name='f' action="/login" method="post">
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
    <script type="text/javascript">
        function validate() {
            if (document.f.username.value === "" && document.f.password.value === "") {
                window.alert("Username and password are required");
                document.f.username.focus();
                return false;
            }
            if (document.f.username.value === "") {
                window.alert("Username is required");
                document.f.username.focus();
                return false;
            }
            if (document.f.password.value === "") {
                window.alert("Password is required");
                document.f.password.focus();
                return false;
            }
        }
    </script>
</@c.page>