var paymentForm = document.getElementById('paymentForm');
paymentForm.addEventListener('submit', payWithPaystack, false);

function payment() {
    let amount = $("#amount").val();
    if (amount !== 500) {
        $('#myerror').html("Amount should be 500 naira only");
    } else {
        payWithPaystack();
    }
}

function payWithPaystack() {

    var handler = PaystackPop.setup({
          key: 'pk_test_d2bbd368bc37028cf44ebebaffed3479c4e86e42',
          email: document.getElementById('email').value,
          amount: 500 * 100,
          currency: 'NGN',
          callback: function(response){
              $.ajax({
                  url: 'http://localhost:8030/verify_transaction?reference='+ response.reference,
                  method: 'get',
                  success: function (response) {
                      console.log(response.data.status)
                  }
              });
          },
        onClose: function() {
              alert('Transaction was not completed, window closed.');
            },
      });
      handler.openIframe();
}

function submitAnswer() {
    let answer = $('input[name="option"]:checked').val();
    let questionId = $('#question-id').val();
    let request = JSON.stringify({answer: answer, questionId: questionId}) ;

    $.ajax({
        url: 'http://localhost:8030/quiz-ans',
        contentType: "application/json",
        method: 'post',
        dataType: 'json',
        data: request,
        success: function() {
            $.get('http://localhost:8030/quiz-ans', function (response) {
                displayNextQuestion(response);
            });
        }
    })
}

function displayNextQuestion(response) {
    if (!response.gameover) {
        $('#question-id').val(response.question.id)
        $('#question').html(response.question.question);
        $('#labelA').html(response.option.optionA);
        $('#labelB').html(response.option.optionB);
        $('#labelC').html(response.option.optionC);
        $('#labelD').html(response.option.optionD);
        $('#optionA').attr('value', response.option.optionA);
        $('#optionB').attr('value', response.option.optionB);
        $('#optionC').attr('value', response.option.optionC);
        $('#optionD').attr('value', response.option.optionD);
    } else {
        window.location = "http://localhost:8030/quiz/gameover";
    }
}

