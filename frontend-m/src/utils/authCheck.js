const authCheck = {
    methods: {
        folderAuthCheck(skillCodes, topMenuCode, menuCode) {
            if (!this.$store.state.menuData) return;
            const GNB = this.$store.state.menuData;
            const TopMenuCode = topMenuCode || this.$route.meta.topMenuCode;
            const MenuCode = menuCode || this.$route.meta.menuCode;
            const index = GNB.findIndex(el => el.menuCode === TopMenuCode);
            if (GNB[index] && MenuCode) {
                const authDisabled = menus => {
                    return menus.some(el => {
                        if (el.menus) {
                            return authDisabled(el.menus);
                        } else {
                            return (
                                el.menuCode === MenuCode.toUpperCase() &&
                                el.skillCodes.some(e => {
                                    return (
                                        e.code === skillCodes &&
                                        e.menuRoleSeq !== 0
                                    );
                                })
                            );
                        }
                    });
                };
                return authDisabled(GNB[index].menus);
            }
        },
    },
};
export { authCheck };
