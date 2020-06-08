export default {
    arr: [],
    defaults: {
        start: 0,
        top: 0,
        zIndex: 20110,
        first: false,
        //headerHeight: $("#header").outerHeight()
    },
    totalHeight: 0,
    hidePosition: 0,
    init: (tt, options) => {
        //const _this = this;
        //const optionsE = $.extend(false, _this.defaults, options);
        console.log(tt.$el)
        window.addEventListener('scroll', function () {
            console.log(options)
        })
    },
}
