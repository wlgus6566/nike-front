import {getCode} from '@/api/code';

const getCategoryList = async (codeName, array) => {
    try {
        const {
            data: { data: response },
        } = await getCode(codeName);

        response.forEach((el) => {
            array.push({
                value: el.code,
                label: el.codeName,
            });
        });
    } catch {}
};
export { getCategoryList };
