<#import "parts/common.ftlh" as c>

<@c.page "Добавление счета">
    <div class="row text-center">
        <form method="post">
            <div>
                <label for="meter" style="font-weight: bold; margin-top: 10px">Показания: </label>
                <input id="meter" type="text" name="meter" style="width: 400px">
                <#if type = "Электричество">
                    кВт.ч
                <#else>
                    куб.м
                </#if>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="margin-top: 10px">Добавить</button>
            <input type="button" class="deleteButton" value="Отмена" onClick='location.href="/address/${addressId}/service/${serviceId}"'>
        </form>
    </div>
</@c.page>