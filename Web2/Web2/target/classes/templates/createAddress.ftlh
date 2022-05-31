<#import "parts/common.ftlh" as c>

<@c.page "Добавление адреса">
    <div class="row text-center g-0">
        <form method="post">
            <div style="margin-left: 2px">
                <label for="city" style="font-weight: bold">Населённый пункт: </label>
                <input id="city" type="text" name="city" style="width: 300px">
            </div>
            <div>
                <label for="shortAddress" style="font-weight: bold; margin-top: 10px">Адрес: </label>
                <input id="shortAddress" type="text" name="shortAddress" style="width: 300px">
            </div>
            <div>
                <label for="numberOfResidents" style="font-weight: bold; margin-top: 10px">Количество проживающих: </label>
                <input id="numberOfResidents" type="text" name="numberOfResidents" style="width: 100px">
                чел.
            </div>
            <div>
                <label for="apartmentSize" style="font-weight: bold; margin-top: 10px">Площадь помещения: </label>
                <input id="apartmentSize" type="text" name="apartmentSize" style="width: 100px">
                кв.м.
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="margin-top: 10px">Создать</button>
            <input type="button" class="deleteButton" value="Отмена" onClick='location.href="/main"'>
        </form>
    </div>
</@c.page>