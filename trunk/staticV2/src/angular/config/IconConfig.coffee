cdojV2
.config([
    "$mdIconProvider",
    ($mdIconProvider) ->
      iconLocation = (set, name, size) ->
        "bower_componentsV2/material-design-icons/#{set}/svg/production/" +
          "ic_#{name}_#{size}.svg"
      addIcon = (set, names) ->
        for name in names
          $mdIconProvider.icon(
            "#{set}:#{name}", iconLocation(set, name, "48px"))
      addIcon("navigation", ["menu"])
  ])