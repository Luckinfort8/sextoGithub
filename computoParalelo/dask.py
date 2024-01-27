import dask.dataframe as dd
import dask.array as da
import dask.bag as db
from dask_ml.model_selection import train_test_split
from dask_ml.preprocessing import StandardScaler
from dask_ml.metrics import r2_score, mean_absolute_error, mean_squared_error
from dask_ml.linear_model import LinearRegression
import xgboost as xgb
from xgboost import XGBRegressor
import plotly.graph_objects as go
from dask.distributed import Client

# Cargar el conjunto de datos
df = dd.read_csv('prices.csv')

# Paso 2: Comprender los datos
df.head()

# Inicializar un cliente para distribuir y paralelizar el cómputo
client = Client()

# Análisis exploratorio de datos (EDA)
# Puedes realizar aquí la exploración y limpieza necesaria, por ejemplo:
# - Convertir la columna 'date' a tipo datetime
# - Manejar valores faltantes
# - Identificar y manejar valores atípicos

# Preparar los datos para el modelo de aprendizaje
# Aquí puedes generar subconjuntos de datos, dividir en conjuntos de entrenamiento y prueba, etc.
df['date'] = dd.to_datetime(df['date'])
df = df.set_index('date')
X = df[['open', 'high', 'low', 'volume', 'adjusted']]
y = df['close']

# Dividir los datos en conjuntos de entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42, shuffle=True)

# Escalar los datos
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Elegir modelos y entrenar

# Modelo 2: XGBoost Regressor
model_xgb = XGBRegressor(objective='reg:squarederror', tree_method='exact')
model_xgb.fit(X_train_scaled.compute(), y_train.compute())

# Modelo 1: Regresión Lineal
model_lr = LinearRegression()
model_lr.fit(X_train_scaled.to_dask_array(), y_train.to_dask_array())

# Realizar predicciones
y_pred_lr = model_lr.predict(X_test_scaled.to_dask_array())
y_pred_xgb = model_xgb.predict(X_test_scaled.compute())

# Métricas de evaluación
y_test_values = y_test.dropna().values.compute()
y_pred_lr_values = y_pred_lr.compute()
y_pred_xgb_values = y_pred_xgb

# Convertir a Dask array con chunks especificados
y_test_values_dask = da.from_array(y_test_values, chunks=len(y_test_values))
y_pred_lr_values_dask = da.from_array(y_pred_lr_values, chunks=len(y_pred_lr_values))
y_pred_xgb_values_dask = da.from_array(y_pred_xgb_values, chunks=len(y_pred_xgb_values))

r2_lr = r2_score(y_test_values_dask, y_pred_lr_values_dask)
mae_lr = mean_absolute_error(y_test_values_dask, y_pred_lr_values_dask)
rmse_lr = mean_squared_error(y_test_values_dask, y_pred_lr_values_dask, squared=False)

r2_xgb = r2_score(y_test_values_dask, y_pred_xgb_values_dask)
mae_xgb = mean_absolute_error(y_test_values_dask, y_pred_xgb_values_dask)
rmse_xgb = mean_squared_error(y_test_values_dask, y_pred_xgb_values_dask, squared=False)

print(f"Linear Regression Metrics:")
print(f"R^2: {r2_lr}")
print(f"MAE: {mae_lr}")
print(f"RMSE: {rmse_lr}")

print(f"\nXGBoost Metrics:")
print(f"R^2: {r2_xgb}")
print(f"MAE: {mae_xgb}")
print(f"RMSE: {rmse_xgb}")

# Visualización con Plotly
fig = go.Figure()

fig.add_trace(go.Scatter(x=X_test.index.compute(), y=y_test.compute(), mode='lines', name='Actual'))
fig.add_trace(go.Scatter(x=X_test.index.compute(), y=y_pred_lr.compute(), mode='lines', name='Linear Regression'))
fig.add_trace(go.Scatter(x=X_test.index.compute(), y=y_pred_xgb, mode='lines', name='XGBoost'))

fig.show()

client.close()