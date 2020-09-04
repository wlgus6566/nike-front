const authCheck = {
    methods: {
        folderAuthCheck(skillCodes) {
            const GNB = this.$store.state.gnbMenuListData;
            const TopMenuCode = this.$route.meta.topMenuCode;
            const MenuCode =
                this.$route.params.pathMatch || this.$route.meta.menuCode;
            const index = GNB.findIndex((el) => el.menuCode === TopMenuCode);

            console.log(GNB);
            console.log(TopMenuCode);

            if (GNB[index] && MenuCode) {
                const authDisabled = GNB[index].menus.some((el) => {
                    let elementMenuCode = el.menuCode.split('_');
                    elementMenuCode =
                        elementMenuCode[elementMenuCode.length - 1];

                    return (
                        elementMenuCode === MenuCode.toUpperCase() &&
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
