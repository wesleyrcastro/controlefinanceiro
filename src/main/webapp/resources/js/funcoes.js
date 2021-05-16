PrimeFaces.locales['pt_BR'] = {
	closeText : 'Fechar',
	prevText : 'Anterior',
	nextText : 'Proximo',
	currentText : 'Começo',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Mario', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sábado' ],
	dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
	dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	weekHeader : 'Semana',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Horas',
	timeText : 'Tempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Data de Hoje',
	ampm : false,
	month : 'Mês',
	week : 'Semana',
	day : 'Dia',
	allDayText : 'Todo Dia'
};

function apenasNumerosPonto(campo) {
	campo.value = campo.value.replace(/[^0-9.]+/, '');
}

function apenasNumerosVirgula(campo) {
	campo.value = campo.value.replace(/[^0-9,]+/, '');
}

function apenasNumeros(campo) {
	campo.value = campo.value.replace(/[^0-9]+/, '');
}


function my_onkeydown_handler(event){
    switch (event.keyCode) {          
     case 116 : // 'F5'
           event.returnValue = false;
           event.keyCode = 0;
           window.status = "Atualização desabilitada (F5)";
           alert("Operação desabilitada : F5");
           return false; 
           break; 
    case 17 : // Ctrl
      keyUpTag = false;
      break;
    case 82 : // R 
      if (keyUpTag) {
           event.returnValue = false;
           event.keyCode = 0;
           window.status = "Atualizaçãoo desabilitada (Ctrl + R)";
           alert("Operação desabilitada : Ctrl + R");
           return false; 
      }
      break;
    case 114 : // r 
      if (keyUpTag) {
           event.returnValue = false;
           event.keyCode = 0;
           window.status = "Atualizaçãoo desabilitada (Ctrl + r)";
           alert("Operação desabilitada : Ctrl + r");
           return false; 
      }
      break;
    }
}


//function configurarMoeda() {
//	$(".moeda").maskMoney({ decimal: ",", thousands: ".", allowZero: true });
//}
//
//$(document).ready(function() {
//	configurarMoeda();
//	$('body').keydown(my_onkeydown_handler)
//});