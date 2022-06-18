<#import "parts/common.ftlh" as c>

<@c.page "Услуга">
    <div class="row g-0">
            <div class="col-lg-10 offset-1 text-center">
                <h1 style="margin-top: 25px; margin-bottom: 25px">Счета</h1>
                <table class="table table-bordered table-hover align-middle table-striped" id="services">
                    <thead>
                    <tr>
                        <th>Номер</th>
                        <th>Дата</th>
                        <th><#if service.type = "Электричество" || service.type = "Газ" || service.type = "Вода">
                                Показания
                            <#elseif service.type = "Капремонт" || service.type = "Отопление">
                                Площадь помещения
                            <#else>
                                Кол-во проживающих
                            </#if>
                        </th>
                        <th>Сумма за месяц</th>
                        <th>Оплаченная сумма</th>
                        <th>Сумма к оплате</th>
                        <th style="width: 275px">Подтверждение</th>
                        <th>Загрузка чека</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list bills?sort_by(["date"])?reverse as bill>
                        <tr>
                            <td>${bill.id}</td>
                            <td>${bill.date.format(formatter)}</td>
                            <td><#if service.type = "Электричество">
                                    ${bill.meter} кВт.ч
                                <#elseif service.type = "Газ" || service.type = "Вода">
                                    ${bill.meter} куб.м
                                <#elseif service.type = "Капремонт" || service.type = "Отопление">
                                    ${address.apartmentSize} кв.м.
                                <#else>
                                    ${address.numberOfResidents} чел.
                                </#if>
                            </td>
                            <td>₽ ${bill.amount}</td>
                            <td>₽ ${bill.paidAmount}</td>
                            <td></td>
                            <td>
                                <label for="payAmount" style="font-weight: bold; margin-top: 10px">₽ </label>
                                <input id="payAmount" type="text" name="payAmount" style="width: 100px">
                                <input type="button" value="Подтвердить" onClick="myFunction(this)">
                                <script type="text/javascript">
                                    function myFunction(el){
                                        let row = el.closest('tr');
                                        let amount = parseFloat(row.cells[5].textContent.substring(2).replace(/\s+/, "").replace(",", "."))
                                        let paymentAmount = row.cells[6].children[1].value;
                                        let billId = row.cells[0].textContent;
                                        if (paymentAmount > amount) {
                                            window.alert("Введённая сумма больше необходимой!")
                                        } else {
                                            location.href = "/address/${service.addressId.id}/service/${service.id}/confirmPayment/" + billId + "/sum=" + paymentAmount;
                                        }
                                    }
                                </script>
                            </td>
                            <td><input type="file" id="file" class="file" value="Загрузить чек" name="file"/></td>
                        </tr>
                    <#else>
                    </#list>
                    </tbody>
                </table>
            </div>
    </div>
    <div class="text-center">
        <#if service.type = "Электричество" || service.type = "Газ" || service.type = "Вода">
            <input type="button" value="Ввести новые показания" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/createBill/${service.type}"'>
        <#else>
            <input type="button" value="Добавить счёт" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/createBill/${service.type}"'>
        </#if>
        <input type="button" value="История платежей" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/history"'>
        <input type="button" value="Назад к услугам" onClick='location.href="/address/${service.addressId.id}"'>
    </div>

    <script type="text/javascript">
        updateSubTotal(); // Initial call

        function updateSubTotal() {
            let table = document.getElementById("services");
            for (let i = 1; i < table.rows.length; i++) {
                let sumVal = 0;
                for (let j = i; j < table.rows.length; j++) {
                    sumVal = sumVal + parseFloat(table.rows[j].cells[3].textContent.substring(2).replace(/\s+/, "").replace(",", ".")) - parseFloat(table.rows[j].cells[4].textContent.substring(2).replace(/\s+/, "").replace(",", "."));
                    table.rows[i].cells[5].innerHTML = "₽ " + sumVal.toLocaleString('en').replace(/,/g,' ').replace(".", ",");
                }
            }
        }
    </script>
</@c.page>