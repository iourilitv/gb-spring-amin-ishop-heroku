var stompClient = null;

/* обновляет страницу без ее перезагрузки */
window.onload = connect();

function connect() {
    /* ВНИМАНИЕ! Нужно обязательно добавлять префикс /amin
    из параметра server.servlet.context-path=/amin в application.properties */
    var socket = new SockJS('/amin/changeQuantity');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/changeQuantity', function(response){
            showResponse(JSON.parse(response.body).prodId,
                JSON.parse(response.body).quantity,
                JSON.parse(response.body).cartItemsQuantity,
                JSON.parse(response.body).cartItemCost,
                JSON.parse(response.body).totalCost
                );
        });
    });
}

function changeQuantity(prodId) {
    stompClient.send("/app/changeQuantity", {},
        JSON.stringify({ 'prodId': prodId,
            // принимаем значение в поле ввода с th:id="${cartItem.product.id}"
            'quantity': $("#quantity" + prodId).val()
                }));
}

function showResponse(prodId, quantity, cartItemsQuantity, cartItemCost, totalCost) {
    console.log(prodId, quantity, cartItemsQuantity);
    // меняем значение атрибута text в элементе a(кнопке Add To Cart) в catalog.html
    // получается текст ссылки-кнопки = Add To Cart({quantity})
    document.getElementById("cart").innerText="Cart(" + cartItemsQuantity + ")";
    document.getElementById("quantity" + prodId).value=quantity;
    document.getElementById("cartItemCost" + prodId).innerText=cartItemCost;
    document.getElementById("totalCost").innerText=totalCost;
}