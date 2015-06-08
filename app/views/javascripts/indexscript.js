$("#instrumento").multiSelect({
  selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Procurar'>",
  selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Procurar'>",
  afterInit: function(ms){
    var that = this,
        $selectableSearch = that.$selectableUl.prev(),
        $selectionSearch = that.$selectionUl.prev(),
        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
    .on('keydown', function(e){
      if (e.which === 40){
        that.$selectableUl.focus();
        return false;
      }
    });

    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
    .on('keydown', function(e){
      if (e.which == 40){
        that.$selectionUl.focus();
        return false;
      }
    });
  },
  afterSelect: function(){
    this.qs1.cache();
    this.qs2.cache();
  },
  afterDeselect: function(){
    this.qs1.cache();
    this.qs2.cache();
  }
});

$("#estilo").multiSelect({
  selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Procurar'>",
  selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Procurar'>",
  afterInit: function(ms){
    var that = this,
        $selectableSearch = that.$selectableUl.prev(),
        $selectionSearch = that.$selectionUl.prev(),
        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
    .on('keydown', function(e){
      if (e.which === 40){
        that.$selectableUl.focus();
        return false;
      }
    });

    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
    .on('keydown', function(e){
      if (e.which == 40){
        that.$selectionUl.focus();
        return false;
      }
    });
  },
  afterSelect: function(){
    this.qs1.cache();
    this.qs2.cache();
  },
  afterDeselect: function(){
    this.qs1.cache();
    this.qs2.cache();
  }
});

function capa(){
	with(document.form1){
		if($("#searchpal").is(':checked')){
			palavrachave.disabled = false;
		}
		else{
			$("#palavrachave").val("");
			palavrachave.disabled = true;
		}
		if($("#searchinst").is(':checked')){
			instrumento.disabled = false;
		}
		else{
			instrumento.disabled = true;
		}
		if($("#searchest").is(':checked')){
			estilo.disabled = false;
		}
		else{
			estilo.disabled = true;
		}
		if($("#searchinter").is(':checked')){
			interesse.disabled = false;
		}
		else{
			interesse.disabled = true;
		}
}}