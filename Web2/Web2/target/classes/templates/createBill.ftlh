<#import "parts/common.ftlh" as c>

<@c.page "Добавление счета">
    <#if error??>
        <#if error = "Введённые показания должны быть больше предыдущих!">
            <script>window.alert("Введённые показания должны быть больше предыдущих!");</script>
        <#elseif error = "Счёт за этот месяц уже добавлен!">
            <script>window.alert("Счёт за этот месяц уже добавлен!");</script>
        </#if>
    </#if>
    <div class="row text-center g-0">
        <#if type = "Электричество" || type = "Газ" || type = "Вода">
            <div><b>Последние показания:</b> ${service.lastMeter}
                <#if type = "Электричество">
                    кВт.ч
                <#else>
                    куб.м
                </#if>
            </div>
            <form method="post">
                <div>
                    <label for="meter" style="font-weight: bold; margin-top: 10px">Новые показания: </label>
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
        <#else>
            <div><b>Дата:</b> ${.now}</div>
            <#if type = "Капремонт" || type = "Отопление">
                <div><b>Площадь помещения:</b> ${address.getApartmentSize()} кв.м.</div>
            <#else>
                <div><b>Количество проживающих:</b> ${address.numberOfResidents} чел.</div>
            </#if>
            <div><b>Сумма к оплате:</b> ${amount} руб.</div>
            <form method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" style="margin-top: 10px">Добавить</button>
                <input type="button" class="deleteButton" value="Отмена" onClick='location.href="/address/${addressId}/service/${serviceId}"'>
            </form>
        </#if>
    </div>
</@c.page>