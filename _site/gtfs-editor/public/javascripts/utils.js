var GtfsEditor = GtfsEditor || {};

(function(G, $) {

  G.Utils = {
    serializeForm : function($form) {
      var data = $form.serializeObject(),
          $checkedCheckboxes = $form.find('[type="checkbox"]:checked');
          $uncheckedCheckboxes = $form.find('[type="checkbox"]:not(:checked)');

      // Handle check box values
      $checkedCheckboxes.each(function(i, el) {
        if (el.name) {
          data[el.name] = true;
        }
      });

      $uncheckedCheckboxes.each(function(i, el) {
        if (el.name) {
          data[el.name] = false;
        }
      });
      
      return data;
    },

    // Messages
    message: function(type, text, title) {

      if(title === undefined) {
        title = text;
        text = '';
      }


      $.pnotify_remove_all();
      
      $.pnotify({
          type: type,
          title: title,
          text: text,
          animate_speed: 'fast'
      });
    },
    
    confirm: function(text) {
      return confirm(text);
    },
    
    success: function(text, delay) { return G.Utils.message('success', text); },
    error: function(text, delay) { return G.Utils.message('error', text); }
  };

})(GtfsEditor, jQuery);
