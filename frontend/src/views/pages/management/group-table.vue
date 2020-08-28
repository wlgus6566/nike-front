<template>
    <table class="group-table">
        <colgroup>
            <col style="width: 175px;" />
            <col style="width: auto;" />
            <col style="width: auto;" />
            <col style="width: auto;" />
            <col style="width: auto;" />
            <col style="width: auto;" />
            <col style="width: auto;" />
        </colgroup>
        <thead>
            <tr>
                <th>
                    <label class="group-check-all">
                        <input
                            type="checkbox"
                            :value="false"
                            v-model="checkAll"
                            @click="allCheckFn()"
                        />
                        <i />
                        {{ table.menuName }}
                    </label>
                </th>
                <th
                    v-for="(thead, theadIndex) in table.skillCodes"
                    :key="theadIndex"
                >
                    {{ thead.message }}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(tr, trIndex) in table.subMenus" :key="trIndex">
                <th v-html="tr.menuName"></th>
                <td v-for="(td, tdIndex) in tr.skillCodes" :key="tdIndex">
                    <label
                        :class="{
                            disabled: !td.menuRoleSeq,
                            checked: menuRoleSeqArray.some(
                                (el) => el === td.menuRoleSeq
                            ),
                        }"
                    >
                        <input
                            type="checkbox"
                            :disabled="!td.menuRoleSeq"
                            :value="td.menuRoleSeq"
                            v-model="menuRoleSeqArray"
                            @click="toggleCheck(td.menuRoleSeq)"
                        />
                        <i />
                    </label>
                </td>
            </tr>
        </tbody>
    </table>
</template>
<script>
export default {
    name: 'GroupTable',
    props: ['table', 'menuRoleSeqArray'],
    data() {
        return {
            checkAll: false,
        };
    },
    watch: {
        menuRoleSeqArray() {
            const totalLength = this.table.subMenus.reduce((a, b) => {
                return a + b.skillCodes.filter((c) => c.menuRoleSeq).length;
            }, 0);
            const checkedLength = this.table.subMenus.reduce((a, b) => {
                const bb = b.skillCodes.reduce((c, d) => {
                    const dd = this.menuRoleSeqArray.some((e) => {
                        return e === d.menuRoleSeq;
                    });
                    return c + (dd ? 1 : 0);
                }, 0);
                return a + bb;
            }, 0);
            this.checkAll = totalLength === checkedLength;
        },
    },
    methods: {
        allCheckFn() {
            let array = [];
            this.table.subMenus.forEach((a) => {
                a.skillCodes.forEach((b) => {
                    if (b.menuRoleSeq) {
                        array.push(b.menuRoleSeq);
                    }
                });
            });

            this.$emit('allCheckFn', array, this.checkAll);
        },
        toggleCheck(seq) {
            this.$emit('toggleCheck', seq);
        },
        checked(tr, td) {
            return tr[td.field] === 'Y';
        },
    },
};
</script>
<style scoped>
.group-name label {
    flex: 0 0 auto;
    width: 100px;
    font-weight: 700;
}
.group-name input {
    flex: 1 1 auto;
}
.group-table {
    table-layout: fixed;
    margin-top: 20px;
    border-collapse: collapse;
    width: 100%;
    border-bottom: 1px solid #ccc;
}
.group-table thead th {
    font-size: 10px;
    padding: 5px 0;
    line-height: 30px;
    font-weight: 700;
    color: #555;
}
.group-table thead th:first-child {
    text-align: left;
    padding: 5px 6px;
}
.group-table thead th label {
    font-size: 14px;
    font-family: 'Roboto', 'Noto Sans KR', 'Arial', 'Apple SD Gothic Neo',
        sans-serif;
    font-weight: 700;
    color: #000;
}
.group-table tbody tr:first-child th,
.group-table tbody tr:first-child td {
    border-top: 1px solid #555;
}

.group-table tbody th {
    text-align: left;
    padding: 0 20px;
    line-height: 50px;
    font-size: 12px;
    letter-spacing: 0;
    font-family: 'Roboto', 'Noto Sans KR', 'Arial', 'Apple SD Gothic Neo',
        sans-serif;
    font-weight: 400;
    color: #000;
    border-top: 1px solid #ddd;
    background: #f7f7f7;
}
.group-table tbody td {
    text-align: center;
    line-height: 50px;
    border-top: 1px solid #ddd;
    border-left: 1px solid #ddd;
    background: #fff;
}
.group-table tbody td label {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 50px;
    width: 100%;
}
.group-table tbody td label input {
    position: absolute;
    opacity: 0;
}
.group-table tbody td label i {
    display: inline-block;
    height: 20px;
    width: 20px;
    background: url('../../../assets/images/svg/icon-btn-check-management-grey.svg')
        no-repeat 50% / contain;
}
.group-table tbody td label.checked i {
    background-image: url('../../../assets/images/svg/icon-btn-check-management-orange.svg');
}
.group-table tbody td label.disabled {
    background: #f7f7f7;
}
.group-check-all {
    line-height: 20px;
}
.group-check-all input {
    opacity: 0;
    position: absolute;
}
.group-check-all i {
    display: inline-block;
    vertical-align: middle;
    height: 20px;
    width: 20px;
    background: url('../../../assets/images/svg/icon-btn-check-management-circle-grey.svg')
        no-repeat 50% / contain;
}
.group-check-all input[type='checkbox']:checked + i {
    background-image: url('../../../assets/images/svg/icon-btn-check-management-circle-orange.svg');
}
</style>
