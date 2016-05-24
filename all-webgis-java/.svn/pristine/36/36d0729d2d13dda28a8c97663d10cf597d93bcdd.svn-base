jQuery.validator.addMethod("rangeLength",
		function(value, element, param) {
			var length = value.length;
			return this.optional(element) || (length >= param[0] && length <= param[1]);
		}, $.validator.format("请确保输入的值在{0}-{1}位之间"));