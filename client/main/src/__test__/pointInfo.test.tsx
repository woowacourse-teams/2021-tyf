import { screen } from '@testing-library/react';
import { userInfoMock } from '../mock/mockData';

import PointInfoPage from '../pages/Point/PointInfo/PointInfoPage';
import { toCommaSeparatedString } from '../utils/format';
import { myRender } from './utils/testUtil';

beforeEach(() => {
  // TODO : localStorage를 mocking하는 best practice 알아보기
  Object.defineProperty(window, 'localStorage', {
    value: { accesstoken: 'fakeAccessToken' },
  });

  myRender(<PointInfoPage />);
});

describe('PointInfoPage', () => {
  test('창작자가 보유한 도네이션 가능한 포인트를 보여준다.', async () => {
    // await screen.findByText(new RegExp(toCommaSeparatedString(userInfoMock.point)));
  });
});
