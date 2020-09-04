const authCheck = {
    methods: {
        authCheckVal(skillCodes) {
            const GNB = this.$store.state.gnbMenuListData;
            const TopMenuCode = this.$route.meta.topMenuCode;
            const MenuCode =
                this.$route.params.pathMatch || this.$route.meta.menuCode;

            const index = GNB.findIndex((el) => el.menuCode === TopMenuCode);
            if (GNB[index] && MenuCode) {
                const authDisabled = GNB[index].menus.some((el) => {
                    return (
                        el.menuName === MenuCode.toUpperCase() &&
                        el.skillCodes.some((e) => {
                            return e.code === skillCodes && e.menuRoleSeq !== 0;
                        })
                    );
                });
                return authDisabled;
            }
        },
    },
};
export { authCheck };
