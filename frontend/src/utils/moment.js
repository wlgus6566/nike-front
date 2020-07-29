export function moment(value) {
    console.log(new Date(value));
    return this.$moment(new Date(value)).format('YYYY.MM.DD');
}
