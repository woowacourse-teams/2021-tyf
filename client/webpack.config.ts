import * as webpack from 'webpack';
import webpackDevServer from 'webpack-dev-server';
import path from 'path';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';

interface WebpackConfig extends webpack.Configuration {
  devServer?: webpackDevServer.Configuration;
}

const isProduction = process.env.NODE_ENV === 'production';

const config: WebpackConfig = {
  entry: path.resolve('src', 'index.tsx'),
  resolve: { extensions: ['.ts', '.tsx', '.js', '.jsx'] },
  output: {
    filename: '[contenthash].bundle.js',
    path: path.resolve('build'),
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true,
            },
          },
        ],
      },
      {
        test: /\.(svg|png|jpg|gif)$/i,
        type: 'asset',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve('public', 'index.html'),
      base: '/',
      favicon: path.resolve('public', 'favicon.ico'),
    }),
    new webpack.ProvidePlugin({
      process: 'process/browser',
    }),
    new webpack.EnvironmentPlugin({
      PUBLIC_URL: path.resolve('public'),
    }),
    new ForkTsCheckerWebpackPlugin({
      typescript: {
        diagnosticOptions: {
          semantic: true,
          syntactic: true,
        },
      },
    }),
  ],
  devServer: {
    contentBase: path.resolve('public'),
    port: 9000,
    historyApiFallback: true,
    open: true,
  },
};

config.mode = isProduction ? 'production' : 'development';

export default config;
