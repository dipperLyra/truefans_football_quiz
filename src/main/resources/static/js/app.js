var paymentForm = document.getElementById('paymentForm');
paymentForm.addEventListener('submit', payWithPaystack, false);

function payWithPaystack() {
    var handler = PaystackPop.setup({
          key: 'pk_test_d2bbd368bc37028cf44ebebaffed3479c4e86e42',
          email: document.getElementById('email').value,
          amount: 500 * 100,
          currency: 'NGN',
          callback: function(response){
              window.location = 'http://localhost:8030/payment/verify?reference='+ response.reference;
          },
          onClose: function() {
              alert('Transaction was not completed, window closed.');
              },
      });
      handler.openIframe();
}

function questionSetUp() {
    let category = $("#formControlLeague").val();
    let subCategory = $("#formControlClub").val();
    let requestBody = JSON.stringify({category:"EPL", subCategory:"Chelsea"})
    console.log("Category : " + category + " sub-category: " + subCategory);

    $.ajax({
        url: 'http://localhost:8030/quiz/setup',
        contentType: "application/json",
        method: 'post',
        dataType: 'json',
        data: requestBody,
        success: function(){console.log("Successful question setup!")}
    })
}

function submitAnswer() {
    let answer = $('input[name="option"]:checked').val();
    let questionId = $('#question-id').val();
    let requestBody = JSON.stringify({answer: answer, questionId: questionId}) ;

    $.ajax({
        url: 'http://localhost:8030/quiz/answer',
        contentType: "application/json",
        method: 'post',
        dataType: 'json',
        data: requestBody,
        success: function() {
            $.get('http://localhost:8030/quiz/answer', function (response) {
                displayNext(response);
            });
        }
    })
}

function displayNext(response) {
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

