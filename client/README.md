# Thank You For: Client

## 🎉 개요

창작자를 위한 간단 도네이션 서비스. Thank You For의 클라이언트 디렉토리입니다.

## 🔨 기술스택

- Typescript
- React
- Recoil
- Styled-component
- Webpack
- Storybook
- React Testing Library
- msw

## ✨ Contributors

- [@jho2301](https://github.com/jho2301)
- [@hchayan](https://github.com/hchayan)

## ⚙️ 사용법

### 0. 설치

```sh
git clone
yarn
```

### 1. 개발서버

```sh
yarn start
```

### 2. 테스트

#### 2-1. Storybook

```sh
yarn storybook
```

##### 2-1-1. storybook build

```sh
yarn build-storybook
```

#### 2-2. React Testing Library

```
yarn test
```

##### 2-2-1. React Testing Library: cache clean

```sh
yarn test:clean
```

### 3. 리액트 프로젝트 빌드

```sh
yarn build
```

#### 3-1. 리액트 개발모드 빌드

```sh
yarn build:dev
```

### 3. 배포용 도커 이미지 빌드

```sh
sudo sh ./docker-build.sh [semver] # ex) 1.0.0
```

### 4. 배포용 도커 인스턴스화

```sh
sudo sh ./docker-start [semver] # ex) 1.0.0
```

