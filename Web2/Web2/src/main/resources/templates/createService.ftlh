<#import "parts/common.ftlh" as c>

<@c.page "Добавление услуги">
    <#if error??>
        <#if error = "Такая услуга уже есть!">
            <script>window.alert("Такая услуга уже есть!");</script>
        </#if>
    </#if>
    <div class="row text-center g-0">
        <form method="post">
            <div style="margin-left: 2px">
                <label for="type" style="font-weight: bold">Тип услуги: </label>
                <select id="type" name="type" size="1">
                    <option value="Вода">Вода</option>
                    <option value="Электричество">Электричество</option>
                    <option value="Газ">Газ</option>
                    <option value="Капремонт">Капремонт</option>
                    <option value="Отопление">Отопление</option>
                    <option value="Вывоз мусора">Вывоз мусора</option>
                </select>
            </div>
            <div>
                <label for="personalAccountNumber" style="font-weight: bold; margin-top: 10px">Лицевой счёт: </label>
                <input id="personalAccountNumber" type="text" name="personalAccountNumber" style="width: 400px">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="margin-top: 10px">Добавить</button>
<#--            <input type="button" class="deleteButton" value="Отмена" onclick="history.back()">-->
            <input type="button" class="deleteButton" value="Отмена" onClick='location.href="/address/${addressId}"'>
        </form>
    </div>
</@c.page>