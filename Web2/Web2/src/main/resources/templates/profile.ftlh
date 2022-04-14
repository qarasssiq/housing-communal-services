<#import "parts/common.ftlh" as c>

<@c.page "Профиль">
    <div class="row text-center g-0">
        <form method="post">
            <div style="margin-top: 10px">Имя пользователя: ${user.username}</div>
            <div style="margin-top: 10px">Роль: ${user.userRole}</div>
            <div style="margin-top: 10px">Имя: ${user.firstname}</div>
            <div style="margin-top: 10px">Фамилия: ${user.lastname}</div>
            <div style="margin-top: 10px">Отчество: ${user.patronymic}</div>
            <div style="margin-top: 10px">Телефон: ${user.phoneNumber}</div>
            <div style="margin-top: 10px">Почта: ${user.email}</div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div style="margin-top: 10px">
                <button type="button">Изменить пароль</button>
            </div>
        </form>
    </div>
</@c.page>