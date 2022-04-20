<#import "parts/common.ftlh" as c>

<@c.page "Регистрация">
    <#if message??>
        <#if message = "User exists!">
            <script>window.alert("User exists!");</script>
        </#if>
    </#if>
    <div class="row" style="margin-top: 200px">
        <div class="col-lg-4 offset-4 text-center loginWindow">
            <form action="/registration" method="post">
                <div class="row">
                    <h1>Регистрация</h1>
                    <div style="margin-top: 20px;">
                        <label style="margin-left: 25px">Имя пользователя : <input type="text" name="username" id="username"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Пароль : <input type="password" name="password" id="password"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Имя : <input type="text" name="firstname" id="firstname"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Фамилия : <input type="text" name="lastname" id="lastname"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Отчество : <input type="text" name="patronymic" id="patronymic"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Телефон : <input type="text" name="phoneNumber" id="phoneNumber"/></label>
                    </div>
                    <div style="margin-top: 20px;">
                        <label>Почта : <input type="text" name="email" id="email"/></label>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <div style="margin-top: 20px;">
                        <input type="submit" value="Зарегистрироваться"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</@c.page>