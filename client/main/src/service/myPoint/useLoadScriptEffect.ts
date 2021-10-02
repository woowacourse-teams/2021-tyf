import { useEffect } from 'react';
import { loadScript } from '../../utils/dynamicImport';

const JQUERY = 'https://code.jquery.com/jquery-1.12.4.min.js';
const IAMPORT = 'https://cdn.iamport.kr/js/iamport.payment-1.1.5.js';

const useLoadScriptEffect = () => {
  useEffect(() => {
    loadScript(JQUERY);
    loadScript(IAMPORT);
  }, []);
};

export default useLoadScriptEffect;
