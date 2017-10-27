/*
        var flashvars = {
             wordID: "1"
	};		
		
		var params = {
                        
			menu: "false",
			scale: "noScale",
			allowFullscreen: "true",
			allowScriptAccess: "always",
			bgcolor: "",
			wmode: "direct" // can cause issues with FP settings & webcam
		};		
		
		var attributes = {
			id:"MainFlash"
		};
		swfobject.embedSWF(
			"./resources/flash/SpellRefinerPlaySound.swf", 
			"altContent", "100", "100", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, 
                        {id:"MainFlash1"});

		swfobject.embedSWF(
			"./resources/flash/SpellRefinerPlaySound.swf", 
			"altContent2", "100", "100", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, {id:"MainFlash2"});

*/

function CreateFlash_PlaySoundByWordID(wordID, tagID){
    
    //alert('CreateFlash_PlaySound');
        var flashvars = {"wordID": wordID};		
		
		var params = {
                        
			menu: "false",
			scale: "noScale",
			allowFullscreen: "true",
			allowScriptAccess: "always",
			bgcolor: "", 
			wmode: "direct" // can cause issues with FP settings & webcam
		};		
		
		var attributes = {
			id:"SpellRefinerPlaySound"
		};
		swfobject.embedSWF(
			"./resources/flash/SpellRefinerPlaySound.swf", 
			tagID, "100", "100", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, attributes);


}


function CreateFlash_PlaySound_ForClass(className){
    
    //alert("CreateFlash_PlaySound_ForClass " + className);
    var x = document.getElementsByClassName(className);
    var i;
    
    //alert("x.length " + x.length);
    for (i = 0; i < x.length; i++) {
        wordID = x[i].getAttribute("data_wordID");
        id = x[i].getAttribute("id");
        //CreateFlash_PlaySoundByWordID(wordID, id);
        
        //alert("wordID " + wordID + "   id " + id);
    }    
    
//var x = document.getElementsByClassName("example");
//var i;
//for (i = 0; i < x.length; i++) {
//    x[i].style.backgroundColor = "red";
//}    


//var x = document.getElementsByTagName("H1")[0].getAttribute("class");

//document.getElementById("demo").id = "newid";
    
}



function Create_SoundPlayer(tagID, objectID){
    //alert('CreateFlash_PlaySound ' + tagID);
    
        var flashvars = {};		
		
		var params = {
                        
			menu: "false",
			scale: "noScale",
			allowFullscreen: "true",
			allowScriptAccess: "always",
			bgcolor: "",
			wmode: "direct" // can cause issues with FP settings & webcam
		};		
		
		var attributes = {
			id:objectID
		};
		swfobject.embedSWF(
			"./resources/flash/SpellRefiner_Player.swf", 
			tagID, "200", "200", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, attributes);


}

function Player_Play_MP3(wordID){
    //alert('play');
    Sound_Player.PlaySound_MP3(wordID);
    return null;
}

function Player_Play_OGG(wordID){
    //alert('play');
    Sound_Player.PlaySound_OGG(wordID);
    return null;
}

function Create_SoundRecorder(tagID, objectID){
    
        var flashvars = {"GetMicrophoneAfterLoading": "true",
        "OnUploadSoundComplete": "",
        "OnUploadSoundFailed": "",
        "OnMicrophoneStatus_Muted": "",
        "OnMicrophoneStatus_Unmuted": "",
        "Testing": "true"
        };
        
        
		
		var params = {
                        
			menu: "false",
			scale: "noScale",
			allowFullscreen: "true",
			allowScriptAccess: "always",
			bgcolor: "",
			wmode: "direct" // can cause issues with FP settings & webcam
		};		
		
		var attributes = {
			id:objectID
		};
		swfobject.embedSWF(
			"./resources/flash/SpellRefiner_Recorder.swf", 
			tagID, "500", "250", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, attributes,
                        function() { 
                        }            
    
                );
}

function Recorder_StartRecording(wordID){
    //alert('1');
    Sound_Recorder.Reсorder_StartRecording(wordID);
    return null;
}

function Recorder_StopAndUpload(){
    //alert('2');
    Sound_Recorder.Reсorder_StopAndUpload();
    
    return null;
}

function Reсorder_GetMicrophone(){
    Sound_Recorder.Reсorder_GetMicrophone();
    return null;
}


//function UploadSoundComplete(){
    //alert('UploadSoundComplete');

//    FormWordRecordSound_Update();
    //hide_elements_by_class('command_stop');
    //show_elements_by_class('command_start');
    
//}

//function UploadSoundFailed(){
    //alert('UploadSoundFailed');
//    FormWordRecordSound_Update();
    //hide_elements_by_class('command_stop');
    //show_elements_by_class('command_start');
    
//}

//function MicrophoneStatus_Muted(){
//    FormWordRecordSound_SetMuted();
    //alert('MicrophoneStatus_Muted');
//}

//function MicrophoneStatus_Unmuted(){
//    FormWordRecordSound_SetUnmuted();
    //set_elements_visibility_by_class("command_start", true);
    //alert('MicrophoneStatus_Unmuted');
//}


function Recorder_StartRecording1(wordID, class_start, class_stop){
    //els_start = document.getElementsByClassName(class_start);
    //els_stop = document.getElementsByClassName(class_stop);
    
    //for (var i=0; i< els_start.length; i++) els_start[i].style.visibility = "hidden";
    //for (var i=0; i< els_stop.length; i++) els_stop[i].style.visibility = "visible";

    
    //set_elements_visibility_by_class(class_start, false);
    //set_elements_visibility_by_class(class_stop, true);
    
    hide_elements_by_class(class_start);
    show_elements_by_class(class_stop);
    
    Sound_Recorder.Reсorder_StartRecording(wordID);
    return null;
}


function Create_SoundRecorderWithParams(tagID, objectID, flashvars){
    
        
        
		
		var params = {
                        
			menu: "false",
			scale: "noScale",
			allowFullscreen: "true",
			allowScriptAccess: "always",
			bgcolor: "#FFFFBB",
			wmode: "direct" // can cause issues with FP settings & webcam
		};		
		
		var attributes = {
			id:objectID
		};
		
		//min width = 250
		//min height = 150		
		swfobject.embedSWF(
			"./resources/flash/SpellRefiner_Recorder.swf", 
			tagID, "400", "150", "10.0.0", 			
			"./resources/flash/expressInstall.swf", 
			flashvars, params, attributes,
                        function() { 
                        }            
    
                );
}



function Recorder_StopRecording1(class_start, class_stop, text_upload_sound){
    
    hide_elements_by_class(class_stop);
    //show_elements_by_class(class_start);
    show_elements_by_class(text_upload_sound);

    Sound_Recorder.Reсorder_StopAndUpload();
    return null;
}
