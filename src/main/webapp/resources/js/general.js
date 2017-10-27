function onload_table_words(){
  //Create_SoundPlayer("Flash_SoundPlayer", "Sound_Player");
  //Create_SoundRecorder("Flash_SoundRecorder", "Sound_Recorder");
 
}

function audio_general_play(wordID){
    audio_element = document.getElementById("audio_general");
    audio_element.src = "word_sounds?wordID=" + wordID;
    //audio_element.load();
    audio_element.play();
    
    return null;
}

function onload_dictionary(){
    //alert('onload_dictionary');
  //Create_SoundPlayer("Flash_SoundPlayer", "Sound_Player");
  //Create_SoundRecorder("Flash_SoundRecorder", "Sound_Recorder");
	
  var flashvars = {"GetMicrophoneAfterLoading": "true",
            "OnUploadSoundComplete": "Dictionary_UploadSoundComplete",
            "OnUploadSoundFailed": "Dictionary_UploadSoundFailed",
            "OnMicrophoneStatus_Muted": "Dictionary_SetMuted",
            "OnMicrophoneStatus_Unmuted": "Dictionary_SetUnmuted",
            "Testing": "false"
            };
       
   Create_SoundRecorderWithParams("Flash_SoundRecorder", "Sound_Recorder", flashvars);
	
   hide_elements_by_class('panel_dict_permissions');
 
}

function Dictionary_SetMuted(){
	FormWord_SetMuted();
	FormDictionaryWords_SetMuted();
	
}

function Dictionary_SetUnmuted(){
	FormWord_SetUnmuted();
	FormDictionaryWords_SetUnmuted();
}

function Dictionary_UploadSoundComplete(){
	show_elements_by_class("class_start");
	FormWord_UploadSoundComplete_UpdateForm();
	FormDictionaryWords_UploadSoundComplete_UpdateList();
}

function Dictionary_UploadSoundFailed(){
	
	FormWord_UploadSoundFailed();
	FormDictionaryWords_UploadSoundFailed();
}





function test_words_show_errors(class_name){
    //alert('test_words_show_errors');
    var details = document.getElementsByClassName(class_name);
    
    for (var i = 0; i < details.length; i++) {
        if (details[i].style.display == 'none') {
            details[i].style.display = 'block';
        } else {
            details[i].style.display = 'none';
        }        
    }    
    
    return false;
 
}

function test_words_show_errors1(detailsId){
    alert('test_words_show_errors1');
    var details = document.getElementById(detailsId);
    
    if (details.style.display == 'none') {
        details.style.display = 'block';
    } else {
        details.style.display = 'none';
    }        
    
    return false;
 
}

/*
function words_show_errors(class_name){
    //alert('test_words_show_errors');
    var details = document.getElementsByClassName(class_name);
    
    for (var i = 0; i < details.length; i++) {
        if (details[i].style.display == 'none') {
            details[i].style.display = 'block';
        } else {
            details[i].style.display = 'none';
        }        
    }    
    
    return false;
 
}

*/


function toggle_elements_visibility_by_class(class_name){
    //alert('toggle_elements_visibility_by_class');
    var items = document.getElementsByClassName(class_name);
    
    for (var i = 0; i < items.length; i++) {
        if (items[i].style.display == 'none') {
            items[i].style.display = 'block';
        } else {
            items[i].style.display = 'none';
        }        
    }    
    
    return false;
 
}

function hide_elements_by_class(class_name){
    //alert('hide_elements_by_class');
    var items = document.getElementsByClassName(class_name);
    
    for (var i = 0; i < items.length; i++) {
        items[i].style.display = 'none';
    }    
    
    return false;
 
}

function show_elements_by_class(class_name){
    //alert('hide_elements_by_class');
    var items = document.getElementsByClassName(class_name);
    
    for (var i = 0; i < items.length; i++) {
        items[i].style.display = 'block';
    }    
    
    return false;
 
}


function onload_dictionary_recordsound(){
    //alert('onload_dictionary_recordsound');
  //Create_SoundPlayer("Flash_SoundPlayer", "Sound_Player");
  //Create_SoundRecorder("Flash_SoundRecorder", "Sound_Recorder");
  
  /*
    var flashvars = {"GetMicrophoneAfterLoading": "true",
        "OnUploadSoundComplete": "UploadSoundComplete",
        "OnUploadSoundFailed": "UploadSoundFailed",
        "OnMicrophoneStatus_Muted": "MicrophoneStatus_Muted",
        "OnMicrophoneStatus_Unmuted": "MicrophoneStatus_Unmuted",
        "Testing": "true"
        };
    */
   
    var flashvars = {"GetMicrophoneAfterLoading": "true",
        "OnUploadSoundComplete": "FormWordRecordSound_UploadSoundComplete",
        "OnUploadSoundFailed": "FormWordRecordSound_UploadSoundFailed",
        "OnMicrophoneStatus_Muted": "FormWordRecordSound_SetMuted",
        "OnMicrophoneStatus_Unmuted": "FormWordRecordSound_SetUnmuted",
        "Testing": "false"
        };
   
    Create_SoundRecorderWithParams("Flash_SoundRecorder", "Sound_Recorder", flashvars);
 
}


function test1(){
    update_form_word_recordsound();
}



function FormWordRecordsound_StopAndUpload(){    
    hide_elements_by_class('command_stop');
    Recorder_StopAndUpload();
    //alert('FormWordRecordsound_StopAndUpload');
    //update_form_word_recordsound();
    //update_form_last_edited_words();
    
    
}

    
    
function set_elements_visibility_by_class(class_name, show){

    var items = document.getElementsByClassName(class_name);
    for (var i=0; i< items.length; i++) {
        if (show==true){
          items[i].style.visibility = "visible";
        }
        else{
          items[i].style.visibility = "hidden";   
        }
    }
    
    return false;
 
}
    

function onload_test(){
	//alert(document.location.host);
	//alert(document.location.pathname);
	
  //Create_SoundPlayer("Flash_SoundPlayer", "Sound_Player");
  Create_SoundRecorder("Flash_SoundRecorder", "Sound_Recorder");
  
  
 
}

/*
function show_word_spelling(button_class, word_spelling_class){
	hide_elements_by_class(button_class);
	show_elements_by_class(word_spelling_class)
}


function hide_word_spelling(button_class, word_spelling_class){
	show_elements_by_class(button_class);
	hide_elements_by_class(word_spelling_class)
}
*/

/*
function form_input_set_focus(){
	document.getElementById('form_input:wordInput').focus();
 
}
*/

function set_focus(id){
	document.getElementById(id).focus(); 
}


function set_hotkeys(){
	
	document.onkeyup=function(e){
		  var e = e || window.event; // for IE to cover IEs window event-object
		  
		  
		  /*
		  //shift+a
		  if(e.shiftKey && e.which == 65) {
			    alert('shift+a');
			    return false;
		  }
		  
		//shift+s
		  if(e.shiftKey && e.which == 83) {
			    alert('shift+s');
			    return false;
		  }
		  
			//shift+d
		  if(e.shiftKey && e.which == 68) {
			    alert('shift+d');
			    return false;
		  }

			//shift+f
		  if(e.shiftKey && e.which == 70) {
			    alert('shift+f');
			    return false;
		  }
		  */
		  
		  /*
		  //alt+a
		  if(e.altKey && e.which == 65) {
			    //alert('alt+a');
			  	//audio_general_play('142');
			    form_input_play();
			    return false;
			  }
		  
		  //alt+s
		  if(e.altKey && e.which == 83) {
			    //alert('alt+s');
			    form_input_save();
			    return false;
			  }

		  //alt+d
		  if(e.altKey && e.which == 68) {
			    //alert('alt+d');
			    form_input_goto_next_word();
			    return false;
			  }
		  
		  */
		  
		  
		  /*
		  //alt+g
		  if(e.altKey && e.which == 71) {
			    return false;
			  }
		  
		  
		  //alt+h
		  if(e.altKey && e.which == 72) {			  	
			    return false;
			  }
			*/  
		  
		  
		  //alt+j
		  if(e.altKey && e.which == 74) {
			  form_input_play();			  
			    return false;
			  }
		  
		  
		  //alt+k
		  if(e.altKey && e.which == 75) {
			  form_input_set_focus();
			    return false;
			  }
		  
		  
		  
		  //alt+l
		  if(e.altKey && e.which == 76) {	
			  form_input_save();
			    return false;
			  }

		  //alert("kew = " +  e.which);
		  //alt+;
		  if(e.altKey && e.which == 59) {
			  
			  form_input_goto_next_word();
			    return false;
			  }
		  
		  //alt+u;
		  if(e.altKey && e.which == 85) {
			  
			  form_input_set_uncertain();
			    return false;
			  }
		  
		  //alt+m		  
		  if(e.altKey && e.which == 77) {
			  
			  form_input_goto_next_word();			  
			    return false;
			  }
		  
		  
		  //alert("e.which = " +  e.which);		
		  //alt+i		  
		  if(e.altKey && e.which == 73) {
			  
			  form_input_show_hidden_word_spelling();			  
			    return false;
			  }
		
		}
	
	
	
}



function onload_check(){
	set_hotkeys();
	
	
}

function onload_index(){
	/*
    //ALT + a
	document.onkeyup=function(e){
		  var e = e || window.event; // for IE to cover IEs window event-object
		  if(e.altKey && e.which == 65) {
		    alert('Keyboard shortcut working!');
		    return false;
		  }
		  
		  if(e.shiftKey && e.which == 65) {
			    alert('shiftKey');
			    return false;
			  }
		  
		}
	
	*/
}


function select_elements_by_class(class_name){

	//var target = document.getElementById('ex5').getElementsByTagName('span')[0];	
	//var target = document.getElementsByClassName(class_name)[0];
	
	var items = document.getElementsByClassName(class_name);
	
	for (var i=0; i< items.length; i++) {
		var target = items[i];
		target.focus();
	
	  var rng, sel;
	  
	  if ( document.createRange ) {
	    rng = document.createRange();
	    rng.selectNode( target )
	    sel = window.getSelection();
	    sel.removeAllRanges();
	    sel.addRange( rng );
	  };
	  
	  /*
	  else {
		  //IE
	    var rng = document.body.createTextRange();
	    rng.moveToElementText( target );
	    rng.select();
	  }	
	  */
	};
	
}



function select_input_text_by_class(class_name){
	var items = document.getElementsByClassName(class_name);
	
    for (var i=0; i< items.length; i++) {
         inputText = items[i];
         
         //inputText.focus();
         inputText.select();	
    }
	
	
}

function set_input_text_by_class(class_name, value){
	var items = document.getElementsByClassName(class_name);
	
    for (var i=0; i< items.length; i++) {
         input = items[i];     
         input.value = value;
         	
    }
	
	
}

/*
function form_input_show_hidden_word_spelling(class_name, text){
	//alert('class_name = ' + class_name);
	//alert('text = ' + text );	
	
	//var class_name = 'hidden_word_spelling';
	set_input_text_by_class(class_name, text);
	
	show_elements_by_class(class_name);
	//select_elements_by_class(class_name);	
	
	select_input_text_by_class(class_name);
	
	
}

*/

function form_input_show_hidden_word_spelling(){
	
	var class_name = 'hidden_word_spelling';
	show_elements_by_class(class_name);
	select_input_text_by_class(class_name);
	
	
}


function onload_quick(){
	set_hotkeys();
	
	
}

