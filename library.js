// Useful for Google / Yahoo maps API

function calculateDistance(startPoint,endPoint) {
        //Radius of the earth in:  1.609344 miles,  6371 km  | var R = (6371 / 1.609344);
        //console.log(startPoint);
        //console.log(endPoint);
       
        var lat1 = startPoint.Lat;
        var lat2 = endPoint.Lat;
        var lon1 = startPoint.Lon;
        var lon2 = endPoint.Lon;
       
        var R = 3958.7558657440545; // Radius of earth in Miles
        var dLat = toRad(lat2-lat1);
        var dLon = toRad(lon2-lon1);
        var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c;
        return d;
    }

    function toRad(Value) {
        /** Converts numeric degrees to radians */
        return Value * Math.PI / 180;
    }

//Gives the difference between 2 dates


function diffDates(date2,date1){
   
    t2 = date2.getTime();
    t1 = date1.getTime();
   
    return parseInt((t2-t1)/(24*3600*1000));
}

//Check a date


function checkDate(dateString){
   
    dateArray = dateString.split('-');
    day = dateArray[0];
   
    // Attention! Javascript consider months in the range 0 - 11
    month = dateArray[1] - 1;
    year = dateArray[2];
   
    // This instruction will create a date object
    sourceDate = new Date(year,month,day);
   
    if(year != sourceDate.getFullYear()){
       
        return false;
    }
    if(month != sourceDate.getMonth()){
       
        return false;
    }
    if(day != sourceDate.getDate()){
       
        return false;
    }
    else{
       
        return dateString;
    }
}

//Format date


function formatDate(dateToFormat){
   
    day = ((dateToFormat.getDate() < 10) ? '0' : '') + dateToFormat.getDate();
    month = ((dateToFormat.getMonth()+1 < 10) ? '0' : '') + (dateToFormat.getMonth()+1);
    year = dateToFormat.getFullYear();
    date = day+'-'+month+'-'+year;
   
    return date;
}

//Add day to a date and return it as a string for date picker


function addDayToDate(dateString,dayToAdd){
   
    dateArray = dateString.split('-');
   
    //Take in consideration the current day hence the '-1'
    day = dateArray[0]-1;
   
    // Attention! Javascript consider months in the range 0 - 11
    month = dateArray[1]-1;
    year = dateArray[2];

    z = new Date(year,month,day);
    z.setDate(z.getDate() + Math.round(dayToAdd));
   
    return z;
}

//Pseudo cast a Date String to a Date Obj


function dateStringToDateObj(dateString){
   
    dateArray = dateString.split('-');
    day = dateArray[0];
   
    // Attention! Javascript consider months in the range 0 - 11
    month = dateArray[1]-1;
    year = dateArray[2];
   
    z = new Date(year,month,day);
    z.setDate(z.getDate());
   
    return z;
}

//Get data from a AccountList popup

 

function retrieveAccountPopup(){
   
    popup = window.open("/account/account-list","_blank","width=740,height=700,toolbar=no,menubar=no,scrollbars=yes");

    return false;
}

//Populate data to a form which contains a 'accountname' field and an 'accounttid' field

function select_account(accountId,accountName){
   
    // Send them to the parent window
    window.opener.$('#accountname').val(accountName);
    window.opener.$('#accountid').val(accountId);
    top.close();
   
    return false;
}

//To configure 2 UI dialog datepickers and display a duration in days in a text box


function configureUIDatePickers(){
   
    $('#startdate_dialog').datepicker({
       
        dateFormat: 'dd-mm-yy',
        firstDay:1,
        showWeek:true,
        changeMonth: true,
        changeYear: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        selectWeek: true,
        onSelect: function(date, datepicker) {
       
            startDate = $('#startdate_dialog').datepicker('getDate');
            $('#enddate_dialog').datepicker( 'option', 'minDate',date);
           
            endDate = $('#enddate_dialog').datepicker('getDate');
           
            diff = diffDates(endDate,startDate);
            $('#duration').val(diff);
        }
    });
   
    $('#enddate_dialog').datepicker({
       
        dateFormat: 'dd-mm-yy',
        firstDay:1,
        showWeek:true,
        changeMonth: true,
        changeYear: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        selectWeek: true,
        onSelect: function(date, datepicker){
       
            //$('#enddate_dialog').datepicker('setDate', date);
            startDate = $('#startdate_dialog').datepicker('getDate');
            endDate = $('#enddate_dialog').datepicker('getDate');
           
            diff = diffDates(endDate,startDate);
            $('#duration').val(diff);
        }
    });
